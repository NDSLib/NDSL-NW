package com.ndsl.nw.bun133.api.mildom.jsons;

import java.net.URL;

public class onAdd {
    public Integer area;
    public Integer avatarDecortaion;
    /**
     * It ll always "onAdd"
     */
    public String cmd;
    public boolean isCorrect(){return cmd.equals("onAdd");}
    public Integer enterroomEffect;
    public Integer isFirstTopup;
    public Integer level;
    public Integer loveCountSum;
    public Integer[] medals;
    public Integer nobleLevel;
    public Integer reqId;
    public Integer roomId;
    public Integer rst;
    public Integer type;
    public Integer userCount;
    public Integer userId;
    public URL userImg;
    public String userName;
}
