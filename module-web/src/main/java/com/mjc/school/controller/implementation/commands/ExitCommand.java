package com.mjc.school.controller.implementation.commands;

import com.mjc.school.controller.interfaces.Command;

public class ExitCommand implements Command {
    @Override
    public boolean execute() {
        System.exit(0);
        return false;
    }
}
