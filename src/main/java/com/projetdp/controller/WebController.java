package com.projetdp.controller;

import com.google.gson.Gson;
import com.projetdp.request.RegistrationUserRequest;
import com.projetdp.service.RegistrationAndUpdateUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
public class WebController {
    private final RegistrationAndUpdateUserService registrationAndUpdateUserService;
    private static final Gson gson = new Gson();

    @GetMapping(path = "/registration")
    public ModelAndView registration() {
        return new ModelAndView("registration");
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationUserRequest request) {
        registrationAndUpdateUserService.register(request);
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @GetMapping(path = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView = new ModelAndView ((authentication == null || authentication instanceof AnonymousAuthenticationToken) ? "login" : "redirect:/welcome");
        return modelAndView;
    }

    @RequestMapping(value = "/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    @RequestMapping(value = "/")
    public ModelAndView oneslash() {
        return new ModelAndView("redirect:/login");
    }
}
