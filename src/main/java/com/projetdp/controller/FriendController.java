package com.projetdp.controller;


import com.google.gson.Gson;
import com.projetdp.model.User;
import com.projetdp.request.IdRequest;
import com.projetdp.service.FriendService;
import com.projetdp.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final NotificationService notificationService;
    private static final Gson gson = new Gson();

    @GetMapping("user/friend/list")
    public ModelAndView listFriends() {
        List<User> users = friendService.listFriends();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("friends", users);
        return new ModelAndView("user/friend/list").addAllObjects(objectMap);
    }

    @GetMapping("user/friend/requests")
    public ModelAndView listFriendRequests() {
        List<User> users = friendService.listFriendRequests();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("notyetfriends", users);
        return new ModelAndView("user/friend/requests").addAllObjects(objectMap);
    }

    @PostMapping("user/friend/request/add")
    public ResponseEntity<?> addFriendRequest(@RequestBody IdRequest request) throws NullPointerException{
        ResponseEntity<?> response;
        if(friendService.addFriendRequest(request.getId())) {
            notificationService.addNotificationBefriend(request.getId());
            response = ResponseEntity.ok(gson.toJson(HttpStatus.OK));
        } else response = ResponseEntity.badRequest().body(gson.toJson("You sent a friend request to yourself OR there is already a friend request between you and him"));
        return response;
    }

    @PostMapping("user/friend/refuse")
    public ResponseEntity<?> refuseFriend(@RequestBody IdRequest request) throws NullPointerException{
        ResponseEntity<?> response;
        if(friendService.refuseFriend(request.getId())) {
            notificationService.addNotificationRefuse(request.getId());
            response = ResponseEntity.ok(gson.toJson(HttpStatus.OK));
        } else response = ResponseEntity.badRequest().body(gson.toJson(HttpStatus.BAD_REQUEST));
        return response;
    }

    @PostMapping("user/friend/add")
    public ResponseEntity<?> addFriend(@RequestBody IdRequest request) throws NullPointerException{
        ResponseEntity<?> response;
        if(friendService.addFriend(request.getId())) {
            notificationService.addNotificationBefriend(request.getId());
            response = ResponseEntity.ok(gson.toJson(HttpStatus.OK));
        } else response = ResponseEntity.badRequest().body(gson.toJson(HttpStatus.BAD_REQUEST));
        return response;
    }

    @PostMapping("user/friend/remove")
    public ResponseEntity<?> removeFriend(@RequestBody IdRequest request) throws NullPointerException{
        ResponseEntity<?> response;
        if(friendService.removeFriend(request.getId())) {
            response = ResponseEntity.ok(gson.toJson(HttpStatus.OK));
            notificationService.addNotificationUnfriend(request.getId());
        } else response = ResponseEntity.badRequest().body(gson.toJson(HttpStatus.BAD_REQUEST));

        return response;
    }

}
