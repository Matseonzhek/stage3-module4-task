package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentDtoResponse {
    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public CommentDtoResponse(Long id, String content, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoResponse that = (CommentDtoResponse) o;
        return id.equals(that.id) && content.equals(that.content) && createdDate.equals(that.createdDate) && updatedDate.equals(that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "CommentDtoResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
