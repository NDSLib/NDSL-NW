package com.ndsl.nw.bun133.api.mildom.jsons;

public class EnterRoom {
    public Integer admin;
    public boolean isAdmin(){return admin!=null && admin!=0;}

    /**
     * It ll always "enterRoom"
     */
    public String cmd;
    public boolean isCorrect(){return cmd!=null && cmd.equals("enterRoom");}
    public Integer fobiddenGlobal;
    public Integer forbidden;
    public Integer reqId;
    public Integer rst;
    public Integer type;
    public Integer userCount;
}
