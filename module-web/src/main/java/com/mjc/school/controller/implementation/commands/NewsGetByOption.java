package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;

public class NewsGetByOption extends NewsBaseCommand implements Command {

    private final Scanner scanner;

    public NewsGetByOption(NewsController newsController, Scanner scanner) {
        super(newsController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        String tagName = "";
        long tagId = 0L;
        String authorName = "";
        String newsTitle = "";
        String newsContent = "";
        System.out.print(Constants.TAG_NAME);
        tagName = scanner.nextLine();
        System.out.print(Constants.TAG_ID);
        if (!scanner.nextLine().isEmpty()) {
            tagId = Long.parseLong(scanner.nextLine());
        }
        System.out.print(Constants.AUTHOR_NAME);
        authorName = scanner.nextLine();
        System.out.print(Constants.NEWS_TITLE);
        newsTitle = scanner.nextLine();
        System.out.print(Constants.NEWS_CONTENT);
        newsContent = scanner.nextLine();
        newsController.getNewsByOption(tagName, tagId, authorName, newsTitle, newsContent).forEach(System.out::println);
        return true;
    }
}
