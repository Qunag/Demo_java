package com.quang.dream_shop.service.user;

import com.quang.dream_shop.exception.AlreadyExistsException;
import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.User;
import com.quang.dream_shop.repository.UserRepository;
import com.quang.dream_shop.request.CreateUserRequest;
import com.quang.dream_shop.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;



    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter( user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User newUser = new User();
                    newUser.setFirstName(req.getFirstName());
                    newUser.setLastName(req.getLastName());
                    newUser.setEmail(req.getEmail());
                    return userRepository.save(newUser);
                }).orElseThrow(() -> new AlreadyExistsException("Email already exists with : " + request.getEmail()));
    }

    @Override
    public User updateUser(UpdateUserRequest user, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(  user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {;
                    throw new ResourceNotFoundException("User not found");
                });
    }
}
