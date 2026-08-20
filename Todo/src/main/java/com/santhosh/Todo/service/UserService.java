package com.santhosh.Todo.service;

import com.santhosh.Todo.dto.LoginRequest;
import com.santhosh.Todo.dto.LoginResponse;
import com.santhosh.Todo.dto.RegisterRequest;
import com.santhosh.Todo.dto.UserResponse;

public interface  UserService {
    String register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UserResponse getCurrentUser();
}
