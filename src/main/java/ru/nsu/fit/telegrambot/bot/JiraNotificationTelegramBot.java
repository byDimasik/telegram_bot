package ru.nsu.fit.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
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
import ru.nsu.fit.telegrambot.model.EventModel;
import ru.nsu.fit.telegrambot.model.enums.CallBackEventType;
import ru.nsu.fit.telegrambot.model.enums.MenuType;
import ru.nsu.fit.telegrambot.repository.EventRepository;
import ru.nsu.fit.telegrambot.service.CallBackService;
import ru.nsu.fit.telegrambot.service.UserRegistrationService;

import java.util.List;

/**
 * Jira Notification Telegram Bot
 */
@Slf4j
@Component
public class JiraNotificationTelegramBot extends TelegramLongPollingBot {

    private static final String TG_PREFIX = "@";
    private final TelegramBotConfig telegramBotConfig;
    private final UserRegistrationService userRegistrationService;
    private final CallBackService callBackService;
    private final EventRepository eventRepository;

    private TelegramBotView manager;

    /**
     * Constructor with spring dependency injection
     *
     * @param telegramBotConfig       {@link TelegramBotConfig} bean
     * @param userRegistrationService {@link UserRegistrationService} bean
     * @param callBackService         {@link CallBackService} bean
     * @param eventRepository         {@link EventRepository} bean
     */
    @Autowired
    public JiraNotificationTelegramBot(TelegramBotConfig telegramBotConfig,
                                       UserRegistrationService userRegistrationService,
                                       CallBackService callBackService,
                                       EventRepository eventRepository) {
        this.telegramBotConfig = telegramBotConfig;
        this.userRegistrationService = userRegistrationService;
        this.callBackService = callBackService;
        this.eventRepository = eventRepository;
        manager = new TelegramBotView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpdateReceived(Update update) {
        userRegistrationService.register(update);

        if (update.hasCallbackQuery()) {

            int pageId = 0;
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String callData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            try {
                MenuType menuType = MenuType.valueOf(callData.toUpperCase());
                SendMessage message = renderMenu(update, menuType);
                replaceMessage(chatId, messageId, message);
                return;
            } catch (IllegalArgumentException e) {
                log.trace("Callback is not a menu item");
            }

            try {
                CallBackEventType callBackEventType = CallBackEventType.valueOf(callData.toUpperCase());
                callBackService.procesCallBackEvent(chatId, callBackEventType);
                SendMessage message = renderMenu(update, callBackEventType.getMenuType());
                replaceMessage(chatId, messageId, message);
                return;
            } catch (IllegalArgumentException e) {
                log.trace("Callback is not a event type");
            }

            if (callData.equals("exit")) {
                replaceMessageWithText(chatId, messageId, "Menu has been closed. If you want to continue, type /menu.");
                return;
            }

            InlineKeyboardBuilder builder = manager.createMenuForPage(pageId);

            builder.setChatId(chatId).setText("Choose action:");
            SendMessage message = builder.build();

            replaceMessage(chatId, messageId, message);
        } else if (update.hasMessage() && update.getMessage().hasText()) {

            if (update.getMessage().getText().equals("/menu") || update.getMessage().getText().equals("/start")) {
                SendMessage message = renderMenu(update, MenuType.BACK);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Can not execute message", e);
                }

            }
        }
    }

    private void replaceMessageWithText(long chatId, long messageId, String text) {
        EditMessageText newMessage = new EditMessageText()
                .setChatId(chatId)
                .setMessageId(Math.toIntExact(messageId))
                .setText(text);
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            log.error("Replace message with text error", e);
        }
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
            log.error("Replace message error", e);
        }
    }

    private SendMessage renderMenu(Update update, MenuType type) {
        long chatId = 0;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        }

        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        manager = new TelegramBotView();
        EventModel eventModel = eventRepository.findByChatId(chatId);
        return type.renderMenu(manager, eventModel);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
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
