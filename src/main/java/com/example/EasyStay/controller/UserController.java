package com.example.EasyStay.controller;


import com.example.EasyStay.dtos.ChangePasswordDto;
import com.example.EasyStay.dtos.UserDto;
import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.responses.GeneralSuccessfulResp;
import com.example.EasyStay.service.UserService;
import com.example.EasyStay.validation.OnCreateUser;
import com.example.EasyStay.validation.OnEditUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserEntity currentUserEntity = userService.getCurrentUserEntity();
        return new ResponseEntity<>(userMapper.mapTo(currentUserEntity), HttpStatus.OK);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserEntity> userEntities = userService.allUsers();
        List<UserDto> userDtos = userEntities.stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
        return new  ResponseEntity<>( userDtos, HttpStatus.OK);
    }

    @PatchMapping("/changePassw")
    public ResponseEntity<GeneralSuccessfulResp> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        GeneralSuccessfulResp changedUserPassword = userService.changeUserPassword(changePasswordDto);
        return new ResponseEntity<>(changedUserPassword, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<UserDto> editCurrUserData(
            @Validated(OnEditUser.class)
            @RequestBody UserDto userDto
    ) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.editCurrUserData(userEntity);
        return new ResponseEntity<>( userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/currUser")
    public ResponseEntity<UserDto> deleteCurrentUser() {
        UserEntity deletedCurrUserAcc = userService.deleteYourUserAccount();
        return new ResponseEntity<>(userMapper.mapTo(deletedCurrUserAcc), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<UserDto> deleteUser(@RequestParam("userId") Long userId) {
        UserEntity updatedUser = userService.deleteUser(userId);
        return new ResponseEntity<>( userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @GetMapping("/searchUserFullName")
    public Page<UserDto> searchUsersByFullName(@RequestParam("fullName") String fullName, Pageable pageable) {
        Page<UserEntity> patients = userService.searchUsersByFullName(fullName, pageable);
        return patients.map(userMapper::mapTo);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> save(@Validated(OnCreateUser.class) @RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.save(userEntity);
        return new ResponseEntity<>( userMapper.mapTo(updatedUser), HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDto> getUser(@RequestParam("username") String username) {
        UserEntity userEntity = userService.getUserByUserName(username);
        return new ResponseEntity<>(userMapper.mapTo(userEntity), HttpStatus.OK);
    }
}
