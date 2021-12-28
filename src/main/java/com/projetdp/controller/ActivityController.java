package com.projetdp.controller;

import com.google.gson.Gson;
import com.projetdp.model.Activity;
import com.projetdp.model.Location;
import com.projetdp.model.User;
import com.projetdp.request.ActivityRequest;
import com.projetdp.service.ActivityService;
import com.projetdp.service.FriendService;
import com.projetdp.service.LocationService;
import com.projetdp.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ActivityController {
    private final ActivityService activityService;
    private final LocationService locationService;
    private static final Gson gson = new Gson();

    @PostMapping("user/activity/add")
    public ResponseEntity<?> addActivity(@RequestBody ActivityRequest request) throws NullPointerException{
        activityService.addActivity(request);
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @GetMapping("user/activity/add")
    public ModelAndView addActivity() {
        return new ModelAndView("user/activity/add");
    }

    @GetMapping("user/activity/list")
    public ModelAndView listActivities() {
        List<Activity> activities = activityService.listActivities();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("activities", activities);
        return new ModelAndView("user/activity/list").addAllObjects(objectMap);
    }
}