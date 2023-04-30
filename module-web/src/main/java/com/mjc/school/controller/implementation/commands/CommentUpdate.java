package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.CommentController;
import com.mjc.school.controller.interfaces.Command;
import com.mjc.school.service.dto.CommentDtoRequest;

import java.util.Scanner;

public class CommentUpdate extends CommentBaseCommand implements Command {

    private final Scanner scanner;

    public CommentUpdate(Scanner scanner, CommentController commentController) {
        super(commentController);
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        System.out.println(Constants.COMMENT_ID);
        long idComment = Long.parseLong(scanner.nextLine());
        System.out.println(Constants.COMMENT_CONTENT);
        String content = scanner.nextLine();
        System.out.println(Constants.NEWS_ID);
        long idNews = Long.parseLong(scanner.nextLine());
        CommentDtoRequest commentDtoRequest = new CommentDtoRequest(idComment, content, idNews);
        System.out.println(commentController.update(commentDtoRequest));
        return true;
    }
}
