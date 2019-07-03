package com.example.yukngaji;

public class itempesan {
    private String text; // message body
    private String memberData; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?

    public itempesan(String text, String memberData, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = memberData;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }
    public itempesan(){

    }

    public String getText() {
        return text;
    }

    public String getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
