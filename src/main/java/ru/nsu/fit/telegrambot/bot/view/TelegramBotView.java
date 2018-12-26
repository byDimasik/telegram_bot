package ru.nsu.fit.telegrambot.bot.view;
import java.util.ArrayList;
import java.util.List;

public class TelegramBotView {
    public static final String CANCEL_ACTION = "cancel";

    private int buttonsPerPage = 6;
    private void setButtonsPerPage(int buttonsPerPage) {
        this.buttonsPerPage = buttonsPerPage;
    }

    private int total;
    private int lastPage;

    private MenuItem btnCancel = new MenuItem("Cancel", CANCEL_ACTION);

    private List<MenuItem> menu = new ArrayList<>();

    private int columnsCount;
    public void setColumnsCount(int columnsCount) {
        this.columnsCount = columnsCount;
    }

    public void init() {
        this.total = menu.size();
        this.lastPage = (int) Math.ceil((double) total / buttonsPerPage) - 1;
    }

    public void addMenuItem(String name, String action) {
        this.menu.add(new MenuItem(name, action));
    }

    private List<MenuItem> getPage(int page) {
        List<MenuItem> pageMenu = new ArrayList<>();

        if (page > lastPage) {
            return pageMenu;
        }

        int start = page* buttonsPerPage;
        int end = (page+1)* buttonsPerPage -1;

        if (start < 0) start = 0;
        if (end >= total) end = total-1;

        for (int i = start; i <= end; i++) {
            pageMenu.add(menu.get(i));
        }

        return pageMenu;
    }


    public InlineKeyboardBuilder createMenuForPage(int pageType) {
        List<MenuItem> pageButtons = getPage(pageType);

        setButtonsPerPage(pageType);
        InlineKeyboardBuilder builder = InlineKeyboardBuilder.create();
        int col = 0;
        int num = 0;
        builder.row();
        for (MenuItem button : pageButtons) {
            builder.button(button.getName(), button.getAction());
            if (++col >= columnsCount) {
                col = 0;
                builder.endRow();
                if (num++ <= pageButtons.size()) {
                    builder.row();
                }
            }
        }
        builder.endRow();

        return builder;
    }

}
