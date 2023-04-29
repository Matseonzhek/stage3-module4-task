package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;


public class NewsGetById extends NewsBaseCommand implements Command {

    private final Scanner scanner;

    public NewsGetById(Scanner scanner, NewsController newsController) {
        super(newsController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.NEWS_ID);
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println(newsController.readById(id));
        return true;
    }
}
