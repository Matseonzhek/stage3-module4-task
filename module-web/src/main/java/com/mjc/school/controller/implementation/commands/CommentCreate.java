package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.CommentController;
import com.mjc.school.controller.interfaces.Command;
import com.mjc.school.service.dto.CommentDtoRequest;

import java.util.Scanner;

public class CommentCreate extends CommentBaseCommand implements Command {

    private final Scanner scanner;

    public CommentCreate(Scanner scanner, CommentController commentController) {
        super(commentController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.COMMENT_CONTENT);
        String content = scanner.nextLine();
        System.out.println(Constants.NEWS_ID);
        long id = Long.parseLong(scanner.nextLine());
        CommentDtoRequest commentDtoRequest = new CommentDtoRequest(null, content, id);
        System.out.println(commentController.create(commentDtoRequest));
        return true;
    }
}
