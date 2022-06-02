package com.example.sample1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public Integer slNo;

    @ColumnInfo(name = "receiver_id")
    public String receiverID;

    @ColumnInfo(name = "sender_id")
    public String senderID;

    @ColumnInfo(name = "message")
    public String messageInfo;

    @ColumnInfo(name = "message_datetime")
    public String messageDatetime;

//    @ColumnInfo(name = "received_from")
//    public String receivedFrom;
//
    @ColumnInfo(name = "send_to")
    public String sendTo;

    @ColumnInfo(name = "image_list")
    public  String imageList;

    public User(Integer slNo, String receiverID,String senderID, String messageInfo, String messageDatetime, String sendTo,String imageList) {
        this.slNo = slNo;
        this.receiverID = receiverID;
        this.senderID=senderID;
        this.messageInfo = messageInfo;
        this.messageDatetime = messageDatetime;
     //   this.receivedFrom = receivedFrom;
        this.sendTo = sendTo;
         this.imageList=imageList;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getSenderID() { return senderID; }

    public void setSenderID(String senderID) { this.senderID = senderID; }


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

//    public String getReceivedFrom() {
//        return receivedFrom;
//    }
//
//    public void setReceivedFrom(String receivedFrom) {
//        this.receivedFrom = receivedFrom;
//    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }
}
