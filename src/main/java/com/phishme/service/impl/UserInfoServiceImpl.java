package com.phishme.service.impl;

import com.phishme.model.UserInfo;
import com.phishme.repository.UserInfoRepository;
import com.phishme.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
        log.warn("User saved to data " +userInfo.getEmail() );
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userInfoRepository.existsByEmail(email);
    }

}
