package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.implementation.TagController;

public abstract class TagBaseCommand {
    protected final TagController tagController;

    public TagBaseCommand(TagController tagController) {
        this.tagController = tagController;
    }
}
