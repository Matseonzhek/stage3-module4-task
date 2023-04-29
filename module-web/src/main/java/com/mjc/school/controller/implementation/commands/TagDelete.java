package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;

public class TagDelete extends TagBaseCommand implements Command {

    private final Scanner scanner;

    public TagDelete(Scanner scanner, TagController tagController) {
        super(tagController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.TAG_ID);
        long id = Long.parseLong(scanner.nextLine());
        tagController.deleteById(id);
        return true;
    }
}
