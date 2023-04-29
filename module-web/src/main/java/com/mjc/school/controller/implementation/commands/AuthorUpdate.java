package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.interfaces.Command;
import com.mjc.school.service.dto.AuthorDtoRequest;

import java.util.Scanner;


public class AuthorUpdate extends AuthorBaseCommand implements Command {

    private final Scanner scanner;

    public AuthorUpdate(Scanner scanner, AuthorController authorController) {
        super(authorController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.AUTHOR_ID);
        long id = Long.parseLong(scanner.nextLine());
        System.out.println(Constants.AUTHOR_NAME);
        String name = scanner.nextLine();
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(id, name);
        System.out.println(authorController.update(authorDtoRequest));
        return true;
    }
}
