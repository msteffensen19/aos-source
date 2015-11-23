package com.advantage.online.store.user.model;

/**
 * @author Binyamin Regev on 17/11/2015.
 */
public enum YesNoReply {

    YES ('Y'),
    NO ('N');

    private char replyTypeChar;

    private String replyTypeString;

    YesNoReply(char replyTypeChar) {
        this.replyTypeChar = replyTypeChar;
    }

    public char getReplyTypeChar() {
        return replyTypeChar;
    }
}
