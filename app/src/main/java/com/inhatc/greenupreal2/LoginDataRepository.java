package com.inhatc.greenupreal2;

public class LoginDataRepository {
    private static LoginDataRepository instance;
    private String userId;

    private LoginDataRepository() {}

    public static synchronized LoginDataRepository getInstance() {
        if (instance == null) {
            instance = new LoginDataRepository();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
