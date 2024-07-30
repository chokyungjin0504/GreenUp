package com.inhatc.greenupreal2;

public class Friend {
    private String profileImageUrl;
    private String email;
    private String name;

    public Friend() {
        // Default constructor required for calls to DataSnapshot.getValue(Friend.class)
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
