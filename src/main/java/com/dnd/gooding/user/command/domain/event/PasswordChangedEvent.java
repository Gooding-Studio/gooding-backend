package com.dnd.gooding.user.command.domain.event;

public class PasswordChangedEvent {

    private String id;
    private String newPassword;

    public PasswordChangedEvent(String id, String newPassword) {
        this.id = id;
        this.newPassword = newPassword;
    }

    public String getId() {
        return id;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
