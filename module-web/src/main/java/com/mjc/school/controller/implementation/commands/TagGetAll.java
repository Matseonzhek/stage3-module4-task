package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.controller.interfaces.Command;

public class TagGetAll extends TagBaseCommand implements Command {


    public TagGetAll(TagController tagController) {
        super(tagController);
    }

    @Override
    public boolean execute() {
        tagController.readAll().forEach(System.out::println);
        return true;
    }
}
