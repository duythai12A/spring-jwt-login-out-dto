package com.example.jwtprj.service;

import com.example.jwtprj.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name);//tìm xem user có trong DB không
    Boolean existsByUsername(String username);//kiểm tra username có trong DB chưa khi tạo dữ liệu
    Boolean existsByEmail(String  email);//kiểm tra email có trong DB chưa khi tạo dữ liệu
    User save(User user);
}
