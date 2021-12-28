package com.projetdp.controller;

import com.google.gson.Gson;
import com.projetdp.model.User;
import com.projetdp.request.UpdateUserRequest;
import com.projetdp.service.RegistrationAndUpdateUserService;
import com.projetdp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final RegistrationAndUpdateUserService registrationAndUpdateUserService;
    private static final Gson gson = new Gson();

    @GetMapping(path = "/user/profile/show")
    public ModelAndView viewUserDetails(@AuthenticationPrincipal User userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return new ModelAndView("user/profile/show").addObject("user", user);
    }

    @GetMapping(path = "/user/profile/edit")
    public ModelAndView editUserDetails(@AuthenticationPrincipal User userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return new ModelAndView("user/profile/edit").addObject("user", user);
    }

    @PostMapping(path = "/user/profile/edit")
    public ResponseEntity<?> editUserDetails(@RequestBody UpdateUserRequest request, @AuthenticationPrincipal User user) {
        registrationAndUpdateUserService.update(request, user);
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @GetMapping(path = "/user/list")
    public ModelAndView listUsers() {
        List<User> users = userService.listAll();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("users", users);
        return new ModelAndView("user/list").addAllObjects(objectMap);
    }
}
