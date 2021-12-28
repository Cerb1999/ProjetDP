package com.projetdp.service;

import com.projetdp.model.Activity;
import com.projetdp.model.Location;
import com.projetdp.model.User;
import com.projetdp.repository.ActivityRepository;
import com.projetdp.repository.LocationRepository;
import com.projetdp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final ActivityRepository activityRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public List<Activity> listAllActivities() {
        return activityRepository.findAllByOrderByStartDesc();
    }

    public List<Location> listAllLocations() {
        return locationRepository.findAll();
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public void removeActivity(long id) {
        Activity activity = activityRepository.getById(id);
        activityRepository.delete(activity);
    }

    public void removeLocation(long id) {
        Location location = locationRepository.getById(id);
        locationRepository.delete(location);
    }

    public boolean removeUser(long id) {
        boolean isSame = id == userRepository.findByUsername(UserService.getLoggedUsername()).getId();
        if(!isSame) userRepository.delete(userRepository.findUserById(id));
        return !isSame;
    }
}
