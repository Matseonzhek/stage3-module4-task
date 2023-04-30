package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.CommentController;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;

public class CommentGetAll extends CommentBaseCommand implements Command {


    public CommentGetAll(Scanner scanner, CommentController commentController) {
        super(commentController);
    }

    @Override
    public boolean execute() {
        commentController.readAll().forEach(System.out::println);
        return true;
    }
}
