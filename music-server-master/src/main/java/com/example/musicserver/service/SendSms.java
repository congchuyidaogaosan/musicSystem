package com.example.musicserver.service;

public interface SendSms {

    public boolean sendSMS(String phoneNum, String code);
}
