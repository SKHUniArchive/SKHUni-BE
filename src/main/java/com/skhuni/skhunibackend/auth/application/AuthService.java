package com.skhuni.skhunibackend.auth.application;


import com.skhuni.skhunibackend.auth.api.dto.response.UserInfo;

public interface AuthService {

    UserInfo getUserInfo(String code);

    String getProvider();
}
