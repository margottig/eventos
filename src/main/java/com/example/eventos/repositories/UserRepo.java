package com.example.eventos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.eventos.models.User;

public interface UserRepo extends CrudRepository<User, Long>{
	 User findByEmail(String email);
}
