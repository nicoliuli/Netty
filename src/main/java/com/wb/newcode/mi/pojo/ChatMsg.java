package com.wb.newcode.mi.pojo;

public class ChatMsg {
    private Integer fromId;
    private Integer toId;
    private String text;
    private int msgType = MsgType.MSG_TYPE_CHATMSG;

    public ChatMsg() {
    }

    public ChatMsg(Integer fromId, Integer toId, String text) {
        this.fromId = fromId;
        this.toId = toId;
        this.text = text;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
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
