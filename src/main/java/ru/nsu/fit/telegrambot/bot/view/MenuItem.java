package ru.nsu.fit.telegrambot.bot.view;


public class MenuItem {

    private String name;
    private String action;


    MenuItem(String name, String action) {
        this.name = name;
        this.action = action;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}