package com.mjc.school.controller.implementation.comandsManagement;

import com.mjc.school.controller.constants.Constants;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.CommentController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.controller.implementation.commands.ExitCommand;
import com.mjc.school.controller.interfaces.Command;
import com.mjc.school.controller.operations.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


@Component
public class CommandsManagement {

    private final NewsController newsController;
    private final AuthorController authorController;
    private final TagController tagController;
    private final CommentController commentController;

    @Autowired
    public CommandsManagement(NewsController newsController, AuthorController authorController, TagController tagController, CommentController commentController) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.tagController = tagController;

        this.commentController = commentController;
    }

    public Command getCommand(Scanner scanner, Integer operationNumber) {
        Operations operations;

        operations = Arrays.stream(Operations.values())
                .filter(operation -> Objects.equals(operation.getOperationNumber(), operationNumber)).
                findFirst().get();

        if (operationNumber >= Constants.NEWS_FIRST_OPERATION && operationNumber <= Constants.NEWS_LAST_OPERATION) {
            return operations.getOperation(scanner, newsController);
        }
        if (operationNumber >= Constants.AUTHOR_FIRST_OPERATION && operationNumber <= Constants.AUTHOR_LAST_OPERATION) {
            return operations.getOperation(scanner, authorController);
        }
        if(operationNumber>=Constants.TAG_FIRST_OPERATION&&operationNumber<=Constants.TAG_LAST_OPERATION){
            return operations.getOperation(scanner,tagController);
        }
        if(operationNumber>=Constants.COMMENT_FIRST_OPERATION&&operationNumber<=Constants.COMMENT_LAST_OPERATION){
            return operations.getOperation(scanner,commentController);
        }
        return new ExitCommand();
    }
}
