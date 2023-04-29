package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.Command;

public class NewsGetAll extends NewsBaseCommand implements Command {

    public NewsGetAll(NewsController newsController) {
        super(newsController);
    }

    @Override
    public boolean execute() {
        newsController.readAll().forEach(System.out::println);
        return true;
    }
}
