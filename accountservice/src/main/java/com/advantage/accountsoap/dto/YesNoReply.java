package com.advantage.accountsoap.dto;

import java.util.ArrayList;
import java.util.List;

public enum YesNoReply {

    YES("Y"),
    NO("N");

    private String replyTypeChar;

    private String replyTypeString;

    YesNoReply(String replyTypeChar) {
        this.replyTypeChar = replyTypeChar;
    }

    public String getReplyTypeChar() {
        return replyTypeChar;
    }

    public static List<String> getAllNames() {
        List<String> values = new ArrayList<>();

        for (YesNoReply a : YesNoReply.values()) {
            values.add(a.name());
        }
        return values;
    }

    public static boolean contains(String test) {

        for (YesNoReply a : YesNoReply.values()) {
            if (a.name().equals(test)) {
                return true;
            }
        }

        return false;
    }

}
