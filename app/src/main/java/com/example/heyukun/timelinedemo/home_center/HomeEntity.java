package com.example.heyukun.timelinedemo.home_center;


import java.util.List;

/**
 * Created by heyukun on 2017/9/28.
 */

public class HomeEntity {
    private String iconUrl;
    private String name;
    private long time;
    private List<String> nineUrlList;
    private List<String> likeNameList;
    private int likeFlag;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getNineUrlList() {
        return nineUrlList;
    }

    public void setNineUrlList(List<String> nineUrlList) {
        this.nineUrlList = nineUrlList;
    }

    public List<String> getLikeNameList() {
        return likeNameList;
    }

    public void setLikeNameList(List<String> likeNameList) {
        this.likeNameList = likeNameList;
    }

    public int getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(int likeFlag) {
        this.likeFlag = likeFlag;
    }
}
