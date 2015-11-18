package com.advantage.online.store.user.model;

/**
 * @author Binyamin Regev on 17/11/2015.
 */
public enum YesNoReply {

    YES ('Y', "Yes"),
    NO ('N', "No");

    private char replyTypeChar;

    private String replyTypeString;

    YesNoReply(char replyTypeChar, String replyTypeString) {
        this.replyTypeChar = replyTypeChar;
        this.replyTypeString = replyTypeString;
    }

    public char getReplyTypeChar() {
        return replyTypeChar;
    }

    public String getReplyTypeString() {
        return replyTypeString;
    }
}
