package com.example.dikid.unsa_m;

public class PostData {

    String PostUserName;
    String PostUserNum;
    String PostUserID;
    String PostTitle;
    String PostTime;
    public String getPostUserName() {
        return PostUserName;
    }

    public void setPostUserName(String postUserName) {
        PostUserName = postUserName;
    }

    public String getPostUserNum() {
        return PostUserNum;
    }

    public void setPostUserNum(String postUserNum) {
        PostUserNum = postUserNum;
    }

    public String getPostUserID() {
        return PostUserID;
    }

    public void setPostUserID(String postUserID) {
        PostUserID = postUserID;
    }

    public String getPostTitle() {
        return PostTitle;
    }

    public void setPostTitle(String postTitle) {
        PostTitle = postTitle;
    }

    public String getPostContents() {
        return PostContents;
    }

    public void setPostContents(String postContents) {
        PostContents = postContents;
    }

    public int getPostIsunknown() {
        return PostIsunknown;
    }

    public void setPostIsunknown(int postIsunknown) {
        PostIsunknown = postIsunknown;
    }

    public int getPostIsvisible() {
        return PostIsvisible;
    }

    public void setPostIsvisible(int postIsvisible) {
        PostIsvisible = postIsvisible;
    }

    public int getPostField() {
        return PostField;
    }

    public void setPostField(int postField) {
        PostField = postField;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }

    public PostData(String postUserName, String postUserNum, String postUserID, String postTitle,
                    String postContents, int postIsunknown, int postIsvisible, int postField, int postKey, String postTime) {
        PostUserName = postUserName;
        PostUserNum = postUserNum;
        PostUserID = postUserID;
        PostTitle = postTitle;
        PostContents = postContents;
        PostIsunknown = postIsunknown;
        PostIsvisible = postIsvisible;
        PostField = postField;
        PostKey = postKey;
        PostTime = postTime;
    }

    String PostContents;
    int PostIsunknown;
    int PostIsvisible;
    int PostField;

    public int getPostKey() {
        return PostKey;
    }

    public void setPostKey(int postKey) {
        PostKey = postKey;
    }


    int PostKey;
}
