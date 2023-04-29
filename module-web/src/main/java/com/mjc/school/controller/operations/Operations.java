package com.mjc.school.controller.operations;

import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.controller.implementation.commands.*;
import com.mjc.school.controller.interfaces.Command;

import java.util.Scanner;

public enum Operations {
    GET_ALL_NEWS(1, "Get all news") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new NewsGetAll((NewsController) controller);
        }
    },
    GET_NEWS_BY_ID(2, "Get news by ID") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new NewsGetById(scanner, (NewsController) controller);
        }
    },
    CREAT_NEWS(3, "Create news") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new NewsCreate(scanner, (NewsController) controller);
        }
    },
    UPDATE_NEWS(4, "Update news") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new NewsUpdate(scanner, (NewsController) controller);
        }
    },
    DELETE_NEWS(5, "Delete news") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new NewsDelete(scanner, (NewsController) controller);
        }
    },
    GET_AUTHOR_BY_NEWS_ID(6, "Get author by news ID") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new AuthorGetByNewsId(scanner, (NewsController) controller);
        }
    },
    GET_TAG_BY_NEWS_ID(7, "Get tags by news ID") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new TagGetByNewsId(scanner, (NewsController) controller);
        }
    },
    GET_NEWS_BY_OPTION(8, "Get news by option") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new NewsGetByOption((NewsController) controller, scanner);
        }
    },
    GET_ALL_AUTHORS(9, "Get all authors") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new AuthorGetAll((AuthorController) controller);
        }
    },
    GET_AUTHOR_BY_ID(10, "Get author by ID") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new AuthorGetById(scanner, (AuthorController) controller);
        }
    },
    CREATE_AUTHOR(11, "Create author") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new AuthorCreate(scanner, (AuthorController) controller);
        }
    },
    UPDATE_AUTHOR(12, "Update author") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new AuthorUpdate(scanner, (AuthorController) controller);
        }
    },
    DELETE_AUTHOR(13, "Delete author") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new AuthorDelete(scanner, (AuthorController) controller);
        }
    },
    GET_ALL_TAG(14, "Get all tags") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new TagGetAll((TagController) controller);
        }
    },
    GET_TAG_BY_ID(15, "Get tag by ID") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new TagGetById(scanner, (TagController) controller);
        }
    },
    CREATE_TAG(16, "Create tag") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new TagCreate(scanner, (TagController) controller);
        }
    },
    UPDATE_TAG(17, "Update tag") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new TagUpdate(scanner, (TagController) controller);
        }
    },
    DELETE_TAG(18, "Delete tag") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new TagDelete(scanner, (TagController) controller);
        }
    },

    EXIT_COMMAND(0, "Exit") {
        @Override
        public <T> Command getOperation(Scanner scanner, T controller) {
            return new ExitCommand();
        }
    };

    private final Integer operationNumber;
    private final String operationName;

    Operations(Integer operationNumber, String operationName) {
        this.operationNumber = operationNumber;
        this.operationName = operationName;
    }

    public abstract <T> Command getOperation(Scanner scanner, T controller);

    public Integer getOperationNumber() {
        return operationNumber;
    }

    public String getOperationName() {
        return operationName;
    }
}