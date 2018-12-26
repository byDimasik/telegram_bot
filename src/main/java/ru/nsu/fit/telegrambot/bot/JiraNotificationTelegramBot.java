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
        manager.addMenuItem("Exit", "exit");

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

        manager.addMenuItem("Create", "commentcreate");
        manager.addMenuItem("Update", "commentupdate");
        manager.addMenuItem("Delete", "commentdelete");
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

            int pageId = 0;
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            if (callData.equals("back")) {
                SendMessage message = renderMenu(update, MenuType.MAIN_MENU);
                replaceMessage(chatId,messageId, message);
                return;
            }
            if (callData.equals("issue")) {
                SendMessage message = renderMenu(update, MenuType.ISSUE);
                replaceMessage(chatId,messageId, message);
                return;
            }
            if (callData.equals("feature")) {
                SendMessage message = renderMenu(update, MenuType.FEATURE);
                replaceMessage(chatId,messageId, message);
                return;

            }
            if (callData.equals("sprint")) {
                SendMessage message = renderMenu(update, MenuType.SPRINT);
                replaceMessage(chatId, messageId, message);
                return;
            }
            if (callData.equals("comment")) {
                SendMessage message = renderMenu(update, MenuType.COMMENT);
                replaceMessage(chatId, messageId, message);
                return;
            }
            if (callData.equals("exit")) {
                replaceMessageWithText(chatId, messageId, "Menu has been closed. If you want to continue, type /menu.");
                return;
            }

            if (callData.equals("issueupdate")) {
            }

            if (callData.equals("issuecrate")) {
            }

            if (callData.equals("issueworklog")) {
            }

            if (callData.equals("commentcreate")) {
            }

            if (callData.equals("commentupdate")) {
            }

            if (callData.equals("commentdelete")) {
            }

            if (callData.equals("featurewath")) {
            }

            if (callData.equals("featurewathissue")) {
            }

            if (callData.equals("featureattachment")) {
            }

            if (callData.equals("featuresubtask")) {
            }

            if (callData.equals("sprintcreate")) {
            }

            if (callData.equals("sprintupdate")) {
            }

            if (callData.equals("sprintstart")) {
            }

            if (callData.equals("sprintdelete")) {
            }


            InlineKeyboardBuilder builder = manager.createMenuForPage(pageId);

            builder.setChatId(chatId).setText("Choose action:");
            SendMessage message = builder.build();

            replaceMessage(chatId, messageId, message);
        } else if (update.hasMessage() && update.getMessage().hasText()) {

            if (update.getMessage().getText().equals("/menu") || update.getMessage().getText().equals("/start")) {
                long chatId = update.getMessage().getChatId();
                long messageId = update.getMessage().getMessageId();
                SendMessage message = renderMenu(update, MenuType.MAIN_MENU);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private SendMessage renderMenu(Update update, MenuType type){
        long chatId = 0;
        if (update.hasMessage())
            chatId = update.getMessage().getChatId();
        if (update.hasCallbackQuery())
            chatId = update.getCallbackQuery().getMessage().getChatId();

        InlineKeyboardBuilder builder;

        switch (type){
            case MAIN_MENU:
                initMenu();
                builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Which type of notification you want to configure:");
                break;
            case ISSUE:
                initIssue();
                builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Issue notifications configurations:");
                break;
            case SPRINT:
                initSprint();
                builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Sprint notifications configurations:");
                break;
            case COMMENT:
                initComment();
                builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Comment notifications configurations:");
                break;
            case FEATURE:
                initFeature();
                builder = manager.createMenuForPage(0);
                builder.setChatId(chatId).setText("Feature notifications configurations:");
                break;
                default: builder = manager.createMenuForPage(0);
        }

        return builder.build();
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
