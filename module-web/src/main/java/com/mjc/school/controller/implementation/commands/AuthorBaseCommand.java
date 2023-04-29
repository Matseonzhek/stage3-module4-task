package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.AuthorController;

public abstract class AuthorBaseCommand {

    protected final AuthorController authorController;

    protected AuthorBaseCommand(AuthorController authorController) {
        this.authorController = authorController;
    }
}
