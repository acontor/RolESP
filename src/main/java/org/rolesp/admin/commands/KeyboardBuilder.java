package org.rolesp.admin.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class KeyboardBuilder {
    private KeyboardBuilder() {
    }

    public static ReplyKeyboardBuilder createReply() {
        return new ReplyKeyboardBuilder();
    }

    public static InlineKeyboardBuilder createInline() {
        return new InlineKeyboardBuilder();
    }
}

class ReplyKeyboardBuilder {

    private List<KeyboardRow> rows = new ArrayList<>();

    ReplyKeyboardBuilder() {
    }

    public ReplyKeyboardBuilder addRow(Function<Row, Row> row) {
        rows.add(row.apply(new Row()));
        return this;
    }

    public ReplyKeyboardMarkup build() {
        return new ReplyKeyboardMarkup().setKeyboard(rows).setResizeKeyboard(true);
    }

    public static class Row extends KeyboardRow {

        private Row() {
        }

        public Row addButton(String text) {
            super.add(text);
            return this;
        }
    }
}

class InlineKeyboardBuilder {

    private List<List<InlineKeyboardButton>> rows = new ArrayList<>();

    InlineKeyboardBuilder() {
    }

    public InlineKeyboardBuilder addRow(Function<Row, Row> row) {
        rows.add(row.apply(new Row()));
        return this;
    }

    public InlineKeyboardMarkup build() {
        return new InlineKeyboardMarkup().setKeyboard(rows);
    }

    public static class Row extends ArrayList<InlineKeyboardButton> {

        private Row() {
        }

        public Row addButton(String text, String callback) {
            super.add(new InlineKeyboardButton(text).setCallbackData(callback));
            return this;
        }
    }
}

