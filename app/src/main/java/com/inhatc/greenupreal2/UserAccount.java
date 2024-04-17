package com.inhatc.greenupreal2;
/*
* 사용자 계정 정보 모델 클래스
* */

public class UserAccount {

    private String idTocken; // Firebase Uid (고유 토큰정보)
    private String emailId; // 이메일 아이디
    private String password; // 비밀번호



    public String getIdTocken() {
        return idTocken;
    }

    public void setIdTocken(String idTocken) {
        this.idTocken = idTocken;
    }


    // 파이어베이스 RealTime 쓸때는 빈 생성자가 필요함
    public UserAccount() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
