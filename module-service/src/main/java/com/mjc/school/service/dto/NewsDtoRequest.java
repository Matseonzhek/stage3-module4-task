package com.mjc.school.service.dto;

import org.springframework.stereotype.Component;

@Component
public class NewsDtoRequest {

    private Long id;
    private String title;
    private String content;
    private long authorId;
    private long tagId;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    private long commentId;

    public NewsDtoRequest() {
    }

    public NewsDtoRequest(Long id, String title, String content, long authorId, long tagId, long commentId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.tagId = tagId;
        this.commentId = commentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "NewsDtoRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", tagId=" + tagId +
                ", commentId=" + commentId +
                '}';
    }
}
