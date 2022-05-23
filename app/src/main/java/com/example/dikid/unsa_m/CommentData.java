package com.example.dikid.unsa_m;

public class CommentData {
    String CommentUserName,CommentUserNum,CommetntUserID,CommentContents,CommentTime;
    int PostID,CommentID,CommentIsunknown,CommentIsvisible;

    public String getCommentUserName() {
        return CommentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        CommentUserName = commentUserName;
    }

    public String getCommentUserNum() {
        return CommentUserNum;
    }

    public void setCommentUserNum(String commentUserNum) {
        CommentUserNum = commentUserNum;
    }

    public String getCommetntUserID() {
        return CommetntUserID;
    }

    public void setCommetntUserID(String commetntUserID) {
        CommetntUserID = commetntUserID;
    }

    public String getCommentContents() {
        return CommentContents;
    }

    public void setCommentContents(String commentContents) {
        CommentContents = commentContents;
    }

    public String getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(String commentTime) {
        CommentTime = commentTime;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
    }

    public int getCommentIsunknown() {
        return CommentIsunknown;
    }

    public void setCommentIsunknown(int commentIsunknown) {
        CommentIsunknown = commentIsunknown;
    }

    public int getCommentIsvisible() {
        return CommentIsvisible;
    }

    public void setCommentIsvisible(int commentIsvisible) {
        CommentIsvisible = commentIsvisible;
    }

    public CommentData( int commentID, int postID, int commentIsunknown, String commentUserName, String commentUserNum, String commetntUserID, int commentIsvisible, String commentTime, String commentContents) {

        CommentUserName = commentUserName;
        CommentUserNum = commentUserNum;
        CommetntUserID = commetntUserID;
        CommentContents = commentContents;
        CommentTime = commentTime;
        PostID = postID;
        CommentID = commentID;
        CommentIsunknown = commentIsunknown;
        CommentIsvisible = commentIsvisible;
    }
}
