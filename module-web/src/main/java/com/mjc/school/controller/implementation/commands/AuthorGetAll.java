package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.interfaces.Command;

public class AuthorGetAll extends AuthorBaseCommand implements Command {

    public AuthorGetAll(AuthorController authorController) {
        super(authorController);
    }

    @Override
    public boolean execute() {
        authorController.readAll().forEach(System.out::println);
        return true;
    }
}
