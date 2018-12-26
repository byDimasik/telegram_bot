package ru.nsu.fit.telegrambot.bot.view;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardBuilder {

        private Long chatId;
        private String text;

        private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        private List<InlineKeyboardButton> row = null;

        private InlineKeyboardBuilder() {}

        static InlineKeyboardBuilder create() {
            return new InlineKeyboardBuilder();
        }

        public void setText(String text) {
            this.text = text;
        }

        public InlineKeyboardBuilder setChatId(Long chatId) {
            this.chatId = chatId;
            return this;
        }

        void row() {
            this.row = new ArrayList<>();
        }

        public void button(String text, String callbackData) {
            row.add(new InlineKeyboardButton().setText(text).setCallbackData(callbackData));
        }

        public void endRow() {
            this.keyboard.add(this.row);
            this.row = null;
        }


        public SendMessage build() {
            SendMessage message = new SendMessage();

            message.setChatId(chatId);
            message.setText(text);

            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

            keyboardMarkup.setKeyboard(keyboard);
            message.setReplyMarkup(keyboardMarkup);

            return message;
        }
    }
