package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;


public class AuthorGetById extends AuthorBaseCommand implements Command {

    private final Scanner scanner;

    public AuthorGetById(Scanner scanner, AuthorController authorController) {
        super(authorController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.AUTHOR_ID);
        long id = Long.parseLong(scanner.nextLine());
        System.out.println(authorController.readById(id));
        return true;
    }
}
