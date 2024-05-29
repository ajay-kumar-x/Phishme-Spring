package com.phishme.service;

import com.phishme.model.UserInfo;

import java.util.List;

public interface UserInfoService {
     void saveUserInfo(UserInfo userInfo);

     List<UserInfo> getAllUsers();

     UserInfo findByEmail(String email);

     Boolean existsByEmail(String email);
}
