package com.ndsl.nw.bun133.api.mildom.jsons;

public class onRecallMsg {
    /**
     * It ll always "onRecallMsg"
     */
    public String cmd;
    public boolean isCorrect(){return cmd!=null && cmd.equals("onRecallMsg");}

    public String msgId;
    public Integer reqId;
    public Integer roomId;
    public Integer rst;
    public Integer type;
    public Integer userId;
}
