package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.NewsController;

public abstract class NewsBaseCommand {

    protected final NewsController newsController;

    protected NewsBaseCommand(NewsController newsController) {
        this.newsController = newsController;
    }
}
