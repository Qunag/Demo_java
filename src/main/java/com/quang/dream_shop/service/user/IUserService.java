package com.quang.dream_shop.service.user;

import com.quang.dream_shop.dto.UserDto;
import com.quang.dream_shop.model.User;
import com.quang.dream_shop.request.CreateUserRequest;
import com.quang.dream_shop.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest user, Long userId);
    void deleteUser(Long userId);


    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
