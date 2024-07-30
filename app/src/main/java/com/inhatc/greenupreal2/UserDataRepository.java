package com.inhatc.greenupreal2;

public class UserDataRepository {
    private static UserDataRepository instance;
    private String profileUrl;
    private String id;
    private String pw;
    private String userName;

    private UserDataRepository() {}

    public static synchronized UserDataRepository getInstance() {
        if (instance == null) {
            instance = new UserDataRepository();
        }
        return instance;
    }

    public String getProfileUrl() { return profileUrl; }
    public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPw() { return pw; }
    public void setPw(String pw) { this.pw = pw; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
