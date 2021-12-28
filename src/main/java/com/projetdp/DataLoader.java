package com.projetdp;

import com.projetdp.model.*;
import com.projetdp.repository.*;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final FriendRepository friendRepository;
    private final ActivityRepository activityRepository;
    private final LocationRepository locationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User("username", bCryptPasswordEncoder.encode("password"),"firstnameuser", "lastnameuser", new Date(), UserRole.USER);
        User admin = new User("admin", bCryptPasswordEncoder.encode("admin"),"firstnameadmin", "lastnameadmin", new Date(), UserRole.ADMIN);
        userRepository.save(user);
        userRepository.save(admin);

        Location location = new Location("fake location", "fake address", "fake city", 6.5, 47);
        locationRepository.save(location);

        Activity activity = new Activity("fake description", new Date(), new Date(), location, user);
        Activity activity2 = new Activity("fake description2", new Date(), new Date(), location, admin);
        activityRepository.save(activity);
        activityRepository.save(activity2);
    }
}
