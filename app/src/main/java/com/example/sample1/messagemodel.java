package com.example.sample1;


import java.util.List;

public class messagemodel {
    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";
    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";

    private String msgContent;

    private String msgType;
    public messagemodel(String msgType, String msgContent) {
        this.msgType = msgType;
        this.msgContent = msgContent;
    }
    public String getMsgContent() {
        return msgContent;
    }
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}


