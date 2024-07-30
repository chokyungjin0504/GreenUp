package com.inhatc.greenupreal2;

public class Contents {
    private String profile;
    private String id;
    private String pw;

    public Contents(String profile, String id, String pw, String userName, boolean favorite) {
        this.profile = profile;
        this.id = id;
        this.pw = pw;
        this.userName = userName;
        this.favorite = favorite;
    }

    private String userName;

    private boolean favorite; // 찜 상태를 나타내는 필드 추가

    // Getter 및 Setter 추가
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    public Contents() {}

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
