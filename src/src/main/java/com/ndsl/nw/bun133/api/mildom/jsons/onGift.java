package com.ndsl.nw.bun133.api.mildom.jsons;

import java.net.URL;

public class onGift {
    public Integer area;
    public Integer avatarDecortaion;
    public Integer category;
    /**
     * It ll always "onGift"
     */
    public String cmd;
    public boolean isCorrect(){return cmd!=null && cmd.equals("onGift");}

    public Integer comboEffect;
    public Integer count;
    public Integer countSum;
    public URL fansBgPic;
    public Integer fansGroupType;
    public Integer fansLevel;
    public String fansName;
    public Integer giftCoin;
    public Integer giftCoinV2;
    public Integer giftId;
    public Integer guestOrder;
    public Integer isFirstTopup;
    public Integer level;
    public Integer[] medals;
    public Integer nobleLevel;
    public Integer reqId;
    public Integer roomId;
    public Integer toAvatarDecortaion;
    public Integer toId;
    public Integer toLevel;
    public String toName;
    public URL toUserImg;
    public Integer type;
    public Integer userId;
    public URL userImg;
    public String userName;
}
