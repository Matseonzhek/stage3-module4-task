package com.mjc.school.repository.constants;

public class Constants {
    public static final Integer NEWS_TITLE_MIN = 5;
    public static final Integer NEWS_TITLE_MAX = 30;
    public static final Integer NEWS_CONTENT_MIN = 5;
    public static final Integer NEWS_CONTENT_MAX = 255;
    public static final Integer AUTHOR_NAME_MIN = 3;
    public static final Integer AUTHOR_NAME_MAX = 15;
    public static final Integer TAG_NAME_MIN = 3;
    public static final Integer TAG_NAME_MAX = 15;
    public static final Integer COMMENT_CONTENT_MIN = 5;
    public static final Integer COMMENT_CONTENT_MAX = 255;

    public static final String NEWS_TITLE_CONSTRAINTS = "Title must have from 5 to 30 symbols.";
    public static final String NEWS_CONTENT_CONSTRAINTS = "News must have from 5 to 255 symbols.";
    public static final String AUTHOR_NAME_CONSTRAINTS = "Author's name must have from 3 to 15 symbols.";
    public static final String TAG_NAME_CONSTRAINTS = "Tag must contain from 3 to 15 symbols";
    public static final String COMMENT_CONTENT_CONSTRAINTS = "Comment must have from 5 to 255 symbols.";

    public Constants() {
    }
}
