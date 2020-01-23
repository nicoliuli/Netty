package com.wb.newcode._02mi.pojo;

public class ChatMsg {
    private int fromId;
    private int toId;
    private String text;
    private int msgType = MsgType.MSG_TYPE_CHATMSG;

    public ChatMsg() {
    }

    public ChatMsg(int fromId, int toId, String text) {
        this.fromId = fromId;
        this.toId = toId;
        this.text = text;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", text='" + text + '\'' +
                '}';
    }
}
