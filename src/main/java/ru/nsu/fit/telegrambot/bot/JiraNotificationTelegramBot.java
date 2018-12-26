package ru.nsu.fit.telegrambot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nsu.fit.telegrambot.bot.view.InlineKeyboardBuilder;
import ru.nsu.fit.telegrambot.bot.view.TelegramBotView;
import ru.nsu.fit.telegrambot.config.TelegramBotConfig;

import java.util.List;

@Component
public class JiraNotificationTelegramBot extends TelegramLongPollingBot {

    private static final String TG_PREFIX = "@";
    private final TelegramBotConfig telegramBotConfig;
    private TelegramBotView manager = new TelegramBotView();

    @Autowired
    public JiraNotificationTelegramBot(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
        init();
    }



    private void replaceMessageWithText(long chatId, long messageId, String text) {
        EditMessageText newMessage = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(Math.toIntExact(messageId))
                .setText(text);
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        manager.setColumnsCount(2);

        manager.addMenuItem("type1", "action 1");
        manager.addMenuItem("type2", "action 2");
        manager.addMenuItem("type3", "action 3");
        manager.addMenuItem("type4", "action 4");
        manager.addMenuItem("cancel", "cancel");

        manager.init();

    }

    private void replaceMessage(long chatId, long messageId, SendMessage message) {
        EditMessageText newMessage = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(Math.toIntExact(messageId))
                .setText(message.getText())
                .setReplyMarkup((InlineKeyboardMarkup) message.getReplyMarkup());
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.hasCallbackQuery());

        if (update.hasCallbackQuery()) {
            System.out.println("pokushay");

            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            if (callData.equals("action 1")) {

                EditMessageText newMessage = new EditMessageText()
                        .setChatId(chatId)
                        .setMessageId(Math.toIntExact(messageId))
                        .setText("action1");
                System.out.println("e boi");
                try {
                    execute(newMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (callData.equals(TelegramBotView.CANCEL_ACTION)) {
                replaceMessageWithText(chatId, messageId, "Cancelled.");

            }
            int pageId = 0;
            InlineKeyboardBuilder builder = manager.createMenuForPage(pageId);

            builder.setChatId(chatId).setText("Choose action:");
            SendMessage message = builder.build();

            replaceMessage(chatId, messageId, message);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("pokushay1");


            if (update.getMessage().getText().equals("/menu")) {

                long chatId = update.getMessage().getChatId();

                InlineKeyboardBuilder builder = manager.createMenuForPage(0);

                builder.setChatId(chatId).setText("Choose action:");
                SendMessage message = builder.build();


                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update: updates) {
            onUpdateReceived(update);

        }
    }

    @Override
    public String getBotUsername() {
        return TG_PREFIX + telegramBotConfig.getName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getToken();
    }
}
