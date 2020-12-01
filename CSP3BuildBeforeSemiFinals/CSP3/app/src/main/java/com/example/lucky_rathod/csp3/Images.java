package com.example.lucky_rathod.csp3;

/**
 * Created by Lucky_Rathod on 12-01-2018.
 */

public class Images {
    String imageUrl,imageDate,imageTime,imageLocationAddress,imageLocationValues,output,completed,completedDate,completedTime,completedBy,user_id,
            ngo,feedBack,widthAndHeight,verified,resend;

    public Images() {
    }

    public Images(String imageUrl, String imageDate, String imageTime, String imageLocationAddress, String imageLocationValues, String output, String completed, String completedDate, String completedTime, String completedBy, String user_id, String feedBack, String widthAndHeight, String verified, String resend) {
        this.imageUrl = imageUrl;
        this.imageDate = imageDate;
        this.imageTime = imageTime;
        this.imageLocationAddress = imageLocationAddress;
        this.imageLocationValues = imageLocationValues;
        this.output = output;
        this.completed = completed;
        this.completedDate = completedDate;
        this.completedTime = completedTime;
        this.completedBy = completedBy;
        this.user_id = user_id;
        this.feedBack = feedBack;
        this.widthAndHeight = widthAndHeight;
        this.verified = verified;
        this.resend = resend;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDate() {
        return imageDate;
    }

    public void setImageDate(String imageDate) {
        this.imageDate = imageDate;
    }

    public String getImageTime() {
        return imageTime;
    }

    public void setImageTime(String imageTime) {
        this.imageTime = imageTime;
    }

    public String getImageLocationAddress() {
        return imageLocationAddress;
    }

    public void setImageLocationAddress(String imageLocationAddress) {
        this.imageLocationAddress = imageLocationAddress;
    }

    public String getImageLocationValues() {
        return imageLocationValues;
    }

    public void setImageLocationValues(String imageLocationValues) {
        this.imageLocationValues = imageLocationValues;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getWidthAndHeight() {
        return widthAndHeight;
    }

    public void setWidthAndHeight(String widthAndHeight) {
        this.widthAndHeight = widthAndHeight;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getResend() {
        return resend;
    }

    public void setResend(String resend) {
        this.resend = resend;
    }
}
