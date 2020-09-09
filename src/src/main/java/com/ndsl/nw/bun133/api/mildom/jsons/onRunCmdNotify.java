package com.ndsl.nw.bun133.api.mildom.jsons;

public class onRunCmdNotify {
    /**
     * It ll always "runCmdNotify"
     */
    public String cmd;
    public boolean isCorrect(){return cmd!=null && cmd.equals("runCmdNotify");}

    public RunCmdNotifyBody runBody;
    public String runCmd;
    public Integer type;

    public class RunCmdNotifyBody{
        public Integer host_id;
        public Integer room_id;
        public Integer user_id;
        public Integer user_level;
        public String user_name;
    }
}