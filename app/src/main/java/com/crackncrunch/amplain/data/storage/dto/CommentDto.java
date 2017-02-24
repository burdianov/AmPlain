package com.crackncrunch.amplain.data.storage.dto;

/**
 * Created by Lilian on 24-Feb-17.
 */

public class CommentDto {

    private String id;
    private int remoteId;
    private String avatar;
    private float raiting;
    private String commentDate;
    private String comment;
    private boolean active;

    public CommentDto() {
        remoteId = 0;
        avatar = "missing avatar";
        raiting = 1;
        commentDate = "";
        comment = "missing comments";
        active = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getRaiting() {
        return raiting;
    }

    public void setRaiting(float raiting) {
        this.raiting = raiting;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
