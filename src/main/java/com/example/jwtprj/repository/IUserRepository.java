package com.example.jwtprj.repository;

import com.example.jwtprj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String name);//tìm xem user có trong DB không
    Boolean existsByUsername(String username);//kiểm tra username có trong DB chưa khi tạo dữ liệu
    Boolean existsByEmail(String  email);//kiểm tra email có trong DB chưa khi tạo dữ liệu

}
