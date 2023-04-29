package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;


public class NewsDelete extends NewsBaseCommand implements Command {
    private final Scanner scanner;

    public NewsDelete(Scanner scanner, NewsController newsController) {
        super(newsController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.NEWS_ID);
        long id = Long.parseLong(scanner.nextLine());
        newsController.deleteById(id);
        return true;
    }
}
