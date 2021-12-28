package com.projetdp.controller;

import com.google.gson.Gson;
import com.projetdp.model.Activity;
import com.projetdp.model.Location;
import com.projetdp.model.User;
import com.projetdp.request.IdRequest;
import com.projetdp.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private static final Gson gson = new Gson();

    @GetMapping("admin/activity/list")
    public ModelAndView listAllActivities() {
        List<Activity> activities = adminService.listAllActivities();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("activities", activities);
        return new ModelAndView("admin/activity/list").addAllObjects(objectMap);
    }

    @GetMapping("admin/user/list")
    public ModelAndView listAllUsers() {
        List<User> users = adminService.listAllUsers();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("users", users);
        return new ModelAndView("admin/user/list").addAllObjects(objectMap);
    }

    @GetMapping("admin/location/list")
    public ModelAndView listAllLocations() {
        List<Location> locations = adminService.listAllLocations();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("locations", locations);
        return new ModelAndView("admin/location/list").addAllObjects(objectMap);
    }


    @PostMapping("admin/activity/remove")
    public ResponseEntity<?> removeActivity(@RequestBody IdRequest request) throws NullPointerException{
        adminService.removeActivity(request.getId());
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @PostMapping("admin/location/remove")
    public ResponseEntity<?> removeLocation(@RequestBody IdRequest request) throws NullPointerException{
        adminService.removeLocation(request.getId());
        return ResponseEntity.ok(gson.toJson(HttpStatus.OK));
    }

    @PostMapping("admin/user/remove")
    public ResponseEntity<?> removeUser(@RequestBody IdRequest request) throws NullPointerException{
        ResponseEntity<?> response;
        if(adminService.removeUser(request.getId())) {
            response = ResponseEntity.ok(gson.toJson(HttpStatus.OK));
        } else response = ResponseEntity.badRequest().body(gson.toJson(HttpStatus.BAD_REQUEST));
        return response;
    }
}
