package com.projetdp.service;

import com.projetdp.model.Activity;
import com.projetdp.model.Location;
import com.projetdp.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final ActivityService activityService;
    private final LocationService locationService;
    private final UserService userService;

    public List<Activity> listAllActivitiesAdmin() {
        return activityService.listAllActivities();
    }

    public List<Location> listAllLocationsAdmin() {
        return locationService.listAllLocations();
    }

    public List<User> listAllUsers() {
        return userService.listAll();
    }

    public void removeActivityAdmin(long id) {
        activityService.removeActivity(id);
    }

    public void removeLocationAdmin(long id) {
        locationService.removeLocation(id);
    }

    public boolean removeUserAdmin(long id) {
        return userService.removeUser(id);
    }
}
