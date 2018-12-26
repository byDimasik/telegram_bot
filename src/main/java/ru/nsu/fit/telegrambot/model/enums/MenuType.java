package ru.nsu.fit.telegrambot.model.enums;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.nsu.fit.telegrambot.bot.view.InlineKeyboardBuilder;
import ru.nsu.fit.telegrambot.bot.view.TelegramBotView;

public enum MenuType {

    BACK("Which type of notification you want to configure:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, Long chatId) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Issue", "issue");
            manager.addMenuItem("Feature", "feature");
            manager.addMenuItem("Sprint", "sprint");
            manager.addMenuItem("Comment", "comment");
            manager.addMenuItem("Exit", "exit");

            manager.init();

            return buildMessage(manager, chatId);
        }
    },
    ISSUE("Issue notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, Long chatId) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Create", "issuecreate");
            manager.addMenuItem("Update", "issueuptade");
            manager.addMenuItem("Delete", "issuedelete");
            manager.addMenuItem("Worklog", "issueworklog");
            manager.addMenuItem("Back", "back");

            manager.init();

            return buildMessage(manager, chatId);
        }
    },
    SPRINT("Sprint notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, Long chatId) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Create", "sprintcreate");
            manager.addMenuItem("Update", "sprintupdate");
            manager.addMenuItem("Delete", "sprintdelete");
            manager.addMenuItem("Start", "sprintstart");
            manager.addMenuItem("Back", "back");

            manager.init();

            return buildMessage(manager, chatId);
        }
    },
    FEATURE("Feature notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, Long chatId) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Watch", "featurewatch");
            manager.addMenuItem("Watch Issue", "featurewatchissue");
            manager.addMenuItem("Subtask", "featuresubtask");
            manager.addMenuItem("Attachment", "featureattachment");
            manager.addMenuItem("Back", "back");

            manager.init();

            return buildMessage(manager, chatId);
        }
    },
    COMMENT("Comment notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, Long chatId) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Create", "commentcreate");
            manager.addMenuItem("Update", "commentupdate");
            manager.addMenuItem("Delete", "commentdelete");
            manager.addMenuItem("Back", "back");

            manager.init();

            return buildMessage(manager, chatId);
        }
    };

    String messageText;

    MenuType(String messageText) {
        this.messageText = messageText;
    }

    public abstract SendMessage renderMenu(TelegramBotView manager, Long chatId);

    SendMessage buildMessage(TelegramBotView manager, Long chatId) {
        InlineKeyboardBuilder builder;

        builder = manager.createMenuForPage(0);
        builder.setChatId(chatId).setText(messageText);

        return builder.build();
    }
}
