package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.Command;
import com.mjc.school.service.dto.NewsDtoRequest;

import java.util.Scanner;

public class NewsUpdate extends NewsBaseCommand implements Command {

    private final Scanner scanner;

    public NewsUpdate(Scanner scanner, NewsController newsController) {
        super(newsController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.NEWS_ID);
        String newsId = scanner.nextLine();
        System.out.println(Constants.NEWS_TITLE);
        String title = scanner.nextLine();
        System.out.println(Constants.NEWS_CONTENT);
        String content = scanner.nextLine();
        System.out.println(Constants.NEWS_AUTHOR_ID);
        long authorId = Long.parseLong(scanner.nextLine());
        System.out.println(Constants.NEWS_TAG_ID);
        long tagId = Long.parseLong(scanner.nextLine());
        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(Long.parseLong(newsId), title, content, authorId, tagId);
        System.out.println(newsController.update(newsDtoRequest));
        return true;
    }
}
