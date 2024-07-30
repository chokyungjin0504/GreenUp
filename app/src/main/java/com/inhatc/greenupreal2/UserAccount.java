package com.inhatc.greenupreal2;


import java.util.HashMap;
import java.util.Map;

public class UserAccount {

    public String emailId; // 이메일 아이디
    public String password; // 비밀번호
    public String name;
    public String phone;

    public UserAccount() {

    }
    public UserAccount(String Name, String Phone_No, String Pw, String Email) {
        this.emailId = Email;
        this.password = Pw;
        this.phone = Phone_No;
        this.name = Name;
    }
    public void mSet_CInfo(String Name, String Phone_No, String Pw, String Email) {
        this.emailId = Email;
        this.password = Pw;
        this.phone = Phone_No;
        this.name = Name;
    }
    public String mGet_CName() {
        return name;
    }
    public String mGet_CPhone_No() {
        return phone;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", name);
        result.put("Phone", phone);
        result.put("Pw", password);
        result.put("Email", emailId);
        return result;
    }

}
