package com.projetdp.controller;

import com.google.gson.Gson;
import com.projetdp.model.Notification;
import com.projetdp.request.NotificationRequest;
import com.projetdp.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
public class NotificationController {
    private final NotificationService notificationService;
    private static final Gson gson = new Gson();

    @GetMapping(value="/user/notification/list")
    public ModelAndView listNotifications(){
        List<Notification> notifications = notificationService.listNotifications();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("notifications", notifications);
        return new ModelAndView("user/notification/list").addAllObjects(objectMap);
    }

    @PostMapping(value="/user/notification/delete")
    public ResponseEntity<?> deleteNotification(@RequestBody NotificationRequest request){
        notificationService.deleteNotification(request.getId());
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @PostMapping(value="/user/notification/read")
    public ResponseEntity<?> readNotfication(@RequestBody NotificationRequest request){
        notificationService.readNotification(request.getId());
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @GetMapping(value="/user/notification/covid")
    public ModelAndView ihaveCovidNotification(){
        notificationService.ihaveCovidNotification();
        return new ModelAndView("redirect:/welcome");
    }
}
