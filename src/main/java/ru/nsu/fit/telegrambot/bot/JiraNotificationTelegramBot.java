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
import ru.nsu.fit.telegrambot.model.Enums.MenuType;

import java.util.List;

@Component
public class JiraNotificationTelegramBot extends TelegramLongPollingBot {

    private static final String TG_PREFIX = "@";
    private final TelegramBotConfig telegramBotConfig;
    private TelegramBotView manager = new TelegramBotView();

    @Autowired
    public JiraNotificationTelegramBot(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
        initMenu();
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


    private void initMenu() {
        manager = new TelegramBotView();
        manager.setColumnsCount(2);

        manager.addMenuItem("Issue", "issue");
        manager.addMenuItem("Feature", "feature");
        manager.addMenuItem("Sprint", "sprint");
        manager.addMenuItem("Comment", "comment");
        manager.addMenuItem("Cancel", "cancel");

        manager.init();
    }
    private void initIssue() {

        manager = new TelegramBotView();
        manager.setColumnsCount(2);

        manager.addMenuItem("Create", "issuecreate");
        manager.addMenuItem("Update", "issueuptade");
        manager.addMenuItem("Delete", "issuedelete");
        manager.addMenuItem("Worklog", "issueworklog");
        manager.addMenuItem("Back", "back");

        manager.init();
    }
    private void initComment() {

        manager = new TelegramBotView();
        manager.setColumnsCount(2);

        manager.addMenuItem("Create", "issueaction 1");
        manager.addMenuItem("Update", "issueaction 2");
        manager.addMenuItem("Delete", "issueaction 3");
        manager.addMenuItem("Back", "back");

        manager.init();
    }
    private void initFeature() {

        manager = new TelegramBotView();
        manager.setColumnsCount(2);

        manager.addMenuItem("Watch", "featurewatch");
        manager.addMenuItem("Watch Issue", "featurewatchissue");
        manager.addMenuItem("Subtask", "featuresubtask");
        manager.addMenuItem("Attachment", "featureattachment");
        manager.addMenuItem("Back", "back");


        manager.init();
    }
    private void initSprint() {

        manager = new TelegramBotView();
        manager.setColumnsCount(2);

        manager.addMenuItem("Create", "sprintcreate");
        manager.addMenuItem("Update", "sprintupdate");
        manager.addMenuItem("Delete", "sprintdelete");
        manager.addMenuItem("Start", "sprintstart");
        manager.addMenuItem("Back", "back");

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

        if (update.hasCallbackQuery()) {

            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            if (callData.equals("back"))
                renderMenu(update, MenuType.MAIN_MENU);
            if (callData.equals("issue")) {

                initIssue();
                InlineKeyboardBuilder builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Issue notifications configuration:");
                SendMessage message = builder.build();
                replaceMessage(chatId, messageId, message);

                return;
            }
            if (callData.equals("feature")) {

                initIssue();
                InlineKeyboardBuilder builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Feature notifications configuration:");
                SendMessage message = builder.build();
                replaceMessage(chatId, messageId, message);

                return;
            }
            if (callData.equals(TelegramBotView.CANCEL_ACTION)) {
                replaceMessageWithText(chatId, messageId, "Cancelled.");
                return;

            }
            int pageId = 0;
            InlineKeyboardBuilder builder = manager.createMenuForPage(pageId);

            builder.setChatId(chatId).setText("Choose action:");
            SendMessage message = builder.build();

            replaceMessage(chatId, messageId, message);
        } else if (update.hasMessage() && update.getMessage().hasText()) {

            if (update.getMessage().getText().equals("/menu") || update.getMessage().getText().equals("/start")) {
                renderMenu(update, MenuType.MAIN_MENU);
            }
        }
    }


    private void renderMenu(Update update, MenuType type){
        long chatId = update.getMessage().getChatId();
        InlineKeyboardBuilder builder = manager.createMenuForPage(0);

        switch (type){
            case MAIN_MENU:
                builder.setChatId(chatId).setText("Which type of notification you want to configure:");
                initMenu();
                break;
            case ISSUE:
                builder.setChatId(chatId).setText("Issue notifications configurations:");
                initIssue();
                break;
            case SPRINT:
                builder.setChatId(chatId).setText("Sprint notifications configurations:");
                initSprint();
                break;
            case COMMENT:
                builder.setChatId(chatId).setText("Comment notifications configurations:");
                initComment();
                break;
            case FEATURE:
                builder.setChatId(chatId).setText("Feature notifications configurations:");
                initFeature();
                break;
        }
        SendMessage message = builder.build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
