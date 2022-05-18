package com.example.sample1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int slNo;

    @ColumnInfo(name = "receiver_id")
    public int receiverID;

    @ColumnInfo(name = "message")
    public String messageInfo;

    @ColumnInfo(name = "message_datetime")
    public String messageDatetime;

    @ColumnInfo(name = "received_from")
    public String receivedFrom;

    @ColumnInfo(name = "send_to")
    public String sendTo;

    public User(int slNo, int receiverID, String messageInfo, String messageDatetime, String receivedFrom, String sendTo) {
        this.slNo = slNo;
        this.receiverID = receiverID;
        this.messageInfo = messageInfo;
        this.messageDatetime = messageDatetime;
        this.receivedFrom = receivedFrom;
        this.sendTo = sendTo;
    }

    public int getSlNo() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public String getMessageDatetime() {
        return messageDatetime;
    }

    public void setMessageDatetime(String messageDatetime) {
        this.messageDatetime = messageDatetime;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
