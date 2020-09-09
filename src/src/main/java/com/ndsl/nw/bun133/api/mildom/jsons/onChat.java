package com.ndsl.nw.bun133.api.mildom.jsons;

import java.net.URL;

public class onChat {
    public Integer area;
    /**
     * It ll always "onChat"
     */
    public String cmd;
    public boolean isCorrect(){return cmd.equals("onChat");}
    public String fansBgPic;
    public Integer fansGroupType;
    public Integer fansLevel;
    public boolean isFan(){return fansLevel!=null;}
    public String fansName;
    public Integer isFirstTopup;
    public Integer level;
    public Integer[] medals;
    public String msg;
    public String msgId;
    public Integer reqId;
    public Integer roomAdmin;
    public boolean isAdmin(){return roomAdmin!=null && roomAdmin!=0;}
    public Integer roomId;
    public Long time;
    public Integer toId;
    public String toName;
    public Integer type;
    public Integer userId;
    public URL userImg;
    public String userName;
}
