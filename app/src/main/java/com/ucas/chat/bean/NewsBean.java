package com.ucas.chat.bean;

import java.io.Serializable;

/**
 * 消息列表Bean
 */
public class NewsBean implements Serializable {
    private String friendName;
    private int friendHeadNum;
    private String lastNewsTime;
    private int isReadNews;
    private String lastNews;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getFriendHeadNum() {
        return friendHeadNum;
    }

    public void setFriendHeadNum(int friendHeadNum) {
        this.friendHeadNum = friendHeadNum;
    }

    public String getLastNewsTime() {
        return lastNewsTime;
    }

    public void setLastNewsTime(String lastNewsTime) {
        this.lastNewsTime = lastNewsTime;
    }

    public int getIsReadNews() {
        return isReadNews;
    }

    public void setIsReadNews(int isReadNews) {
        this.isReadNews = isReadNews;
    }

    public String getLastNews() {
        return lastNews;
    }

    public void setLastNews(String lastNews) {
        this.lastNews = lastNews;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "friendName='" + friendName + '\'' +
                ", friendHeadNum=" + friendHeadNum +
                ", lastNewsTime='" + lastNewsTime + '\'' +
                ", isReadNews=" + isReadNews +
                ", lastNews='" + lastNews + '\'' +
                '}';
    }
}
