package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.CommentController;

public abstract class CommentBaseCommand {
    protected final CommentController commentController;

    protected CommentBaseCommand(CommentController commentController) {
        this.commentController = commentController;
    }
}
