package com.example.EasyStay.service;


import com.example.EasyStay.dtos.ChangePasswordDto;
import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.responses.GeneralSuccessfulResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserEntity> allUsers();

    UserEntity getCurrentUserEntity();

    GeneralSuccessfulResp changeUserPassword(ChangePasswordDto changePasswordDto);

    UserEntity deleteYourUserAccount();

    UserEntity deleteUser(Long userId);

    UserEntity editCurrUserData(UserEntity userEntity);

    Page<UserEntity> searchUsersByFullName(String fullName, Pageable pageable);

    UserEntity save(UserEntity userEntity);

    UserEntity getUserByUserName(String username);

}