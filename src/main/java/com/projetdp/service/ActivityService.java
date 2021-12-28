package com.projetdp.service;

import com.projetdp.model.*;
import com.projetdp.repository.ActivityRepository;
import com.projetdp.request.ActivityRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final LocationService locationService;
    private final UserService userService;

    public void addActivity(ActivityRequest request) {
        Location location = locationService.getLocationByAddress(request.getLocation());
        User user =  userService.getUserByUsername(UserService.getLoggedUsername());
        activityRepository.save(
                new Activity(
                        request.getDescription(),
                        request.getStart(),
                        request.getEnd(),
                        location,
                        user
                        )
        );
    }

    public List<Activity> listActivities() {
        User user =  userService.getUserByUsername(UserService.getLoggedUsername());
        return activityRepository.findAllByUser(user);
    }

    public List<Activity> findAllByUserAndStartBetween(User coviduser, Date start, Date end) {
        return activityRepository.findAllByUserAndStartBetween(coviduser, start, end);
    }

    public List<Activity> findAllByLocationAndUserNot(Location location, User user) {
        return activityRepository.findAllByLocationAndUserNot(location, user);
    }

    public List<Activity> findAllByLocationAndUserNotAndStartBetween(Location location, User user, Date start, Date end) {
        return activityRepository.findAllByLocationAndUserNotAndStartBetween(location, user, start, end);
    }
}