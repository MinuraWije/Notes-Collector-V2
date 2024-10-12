package com.example.NotesCollector_V2.dao;


import com.example.NotesCollector_V2.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao  extends JpaRepository<UserEntity,String> {
}
