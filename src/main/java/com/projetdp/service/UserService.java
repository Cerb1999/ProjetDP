package com.projetdp.service;

import com.projetdp.exception.PasswordNotFoundException;
import com.projetdp.model.User;
import com.projetdp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND = "user with %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!userRepository.existsByUsername(username)) throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        return userRepository.findByUsername(username);
    }

    public User getUserByUsername(String username) {
        if(!userRepository.existsByUsername(username)) throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        return userRepository.findByUsername(username);
    }

    @SneakyThrows
    public User login(String username, String password) {
        User user = getUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) throw new PasswordNotFoundException("Invalid user name and password combination.");
        return user;
    }

    public void addUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())) throw new IllegalStateException("username already taken");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    public void updateUser(User newUserDetails, User loggedUser) {
        String encodedPassword = bCryptPasswordEncoder.encode(newUserDetails.getPassword());
        newUserDetails.setPassword(encodedPassword);
        User currentUser = userRepository.getById(loggedUser.getId());
        loggedUser.clone(newUserDetails);
        currentUser.clone(newUserDetails);
        userRepository.save(currentUser);
    }

    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public static String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
