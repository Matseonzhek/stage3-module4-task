package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.controller.interfaces.Command;
import com.mjc.school.service.dto.TagDtoRequest;

import java.util.Scanner;

public class TagCreate extends TagBaseCommand implements Command {

    private final Scanner scanner;

    public TagCreate(Scanner scanner, TagController tagController) {
        super(tagController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.TAG_NAME);
        String name = scanner.nextLine();
        TagDtoRequest tagDtoRequest = new TagDtoRequest(null, name);
        System.out.println(tagController.create(tagDtoRequest));
        return true;
    }
}