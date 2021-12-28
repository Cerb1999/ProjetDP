package com.projetdp.service;

import com.projetdp.request.RegistrationUserRequest;
import com.projetdp.model.User;
import com.projetdp.model.UserRole;
import com.projetdp.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationAndUpdateUserService {
    private final UserService userService;

    public void register(RegistrationUserRequest request) {
        userService.addUser(
                new User(
                        request.getUsername(),
                        request.getPassword(),
                        request.getFirstname(),
                        request.getLastname(),
                        request.getBirthdate(),
                        UserRole.USER
                )
        );
    }

    public void update(UpdateUserRequest request, User loggedUser) {
        userService.updateUser(
                new User(
                        request.getUsername(),
                        request.getPassword(),
                        request.getFirstname(),
                        request.getLastname(),
                        request.getBirthdate(),
                        request.getRole()
                ),
                loggedUser
        );
    }
}
