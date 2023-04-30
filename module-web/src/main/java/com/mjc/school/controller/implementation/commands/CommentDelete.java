package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.CommentController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;

public class CommentDelete extends CommentBaseCommand implements Command {
    private final Scanner scanner;

    public CommentDelete(Scanner scanner, CommentController commentController) {
        super(commentController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.COMMENT_ID);
        long id = Long.parseLong(scanner.nextLine());
        commentController.deleteById(id);
        return true;
    }
}
