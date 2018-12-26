package ru.nsu.fit.telegrambot.model.enums;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.nsu.fit.telegrambot.bot.view.InlineKeyboardBuilder;
import ru.nsu.fit.telegrambot.bot.view.TelegramBotView;
import ru.nsu.fit.telegrambot.model.EventModel;

import static ru.nsu.fit.telegrambot.model.enums.CallBackEventType.*;

public enum MenuType {

    BACK("Which type of notification you want to configure:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, EventModel eventModel) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Issue", "issue");
            manager.addMenuItem("Feature", "feature");
            manager.addMenuItem("Sprint", "sprint");
            manager.addMenuItem("Comment", "comment");
            manager.addMenuItem("Exit", "exit");

            manager.init();

            return buildMessage(manager, eventModel.getChatId());
        }
    },
    ISSUE("Issue notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, EventModel eventModel) {
            manager.setColumnsCount(2);

            manager.addMenuItem(ISSUE_CREATE.getButtonText(), ISSUE_CREATE.name());
            manager.addMenuItem(ISSUE_UPDATE.getButtonText(), ISSUE_UPDATE.name());
            manager.addMenuItem(ISSUE_DELETE.getButtonText(), ISSUE_DELETE.name());
            manager.addMenuItem(ISSUE_WORK_LOG.getButtonText(), ISSUE_WORK_LOG.name());
            manager.addMenuItem(CallBackEventType.BACK.getButtonText(), ru.nsu.fit.telegrambot.model.enums.CallBackEventType.BACK.name());

            manager.init();

            return buildMessage(manager, eventModel.getChatId());
        }
    },
    SPRINT("Sprint notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, EventModel eventModel) {
            manager.setColumnsCount(2);

            manager.addMenuItem(SPRINT_CREATE.getButtonText(), SPRINT_CREATE.name());
            manager.addMenuItem(SPRINT_UPDATE.getButtonText(), SPRINT_UPDATE.name());
            manager.addMenuItem(SPRINT_DELETE.getButtonText(), SPRINT_DELETE.name());
            manager.addMenuItem(SPRINT_START.getButtonText(), SPRINT_START.name());
            manager.addMenuItem(CallBackEventType.BACK.getButtonText(), CallBackEventType.BACK.name());

            manager.init();

            return buildMessage(manager, eventModel.getChatId());
        }
    },
    FEATURE("Feature notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, EventModel eventModel) {
            manager.setColumnsCount(2);

            manager.addMenuItem("Watch", "featurewatch");
            manager.addMenuItem("Watch Issue", "featurewatchissue");
            manager.addMenuItem("Subtask", "featuresubtask");
            manager.addMenuItem("Attachment", "featureattachment");
            manager.addMenuItem(CallBackEventType.BACK.getButtonText(), CallBackEventType.BACK.name());

            manager.init();

            return buildMessage(manager, eventModel.getChatId());
        }
    },
    COMMENT("Comment notifications configurations:") {
        @Override
        public SendMessage renderMenu(TelegramBotView manager, EventModel eventModel) {
            manager.setColumnsCount(2);

            manager.addMenuItem(COMMENT_CREATE.getButtonText(), COMMENT_CREATE.name());
            manager.addMenuItem(COMMENT_UPDATE.getButtonText(), COMMENT_UPDATE.name());
            manager.addMenuItem(COMMENT_DELETE.getButtonText(), COMMENT_DELETE.name());
            manager.addMenuItem(CallBackEventType.BACK.getButtonText(), CallBackEventType.BACK.name());

            manager.init();

            return buildMessage(manager, eventModel.getChatId());
        }
    };

    private String messageText;

    MenuType(String messageText) {
        this.messageText = messageText;
    }

    public abstract SendMessage renderMenu(TelegramBotView manager, EventModel eventModel);

    SendMessage buildMessage(TelegramBotView manager, Long chatId) {
        InlineKeyboardBuilder builder;

        builder = manager.createMenuForPage(0);
        builder.setChatId(chatId).setText(messageText);

        return builder.build();
    }
}
