package com.projetdp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.projetdp.model.*;
import com.projetdp.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final FriendService friendService;
    private final ActivityService activityService;
    private static final int DAYS_COVID = 5;

    public List<Notification> listNotifications(){
        User currentUser = userService.getUserByUsername(UserService.getLoggedUsername());
        return notificationRepository.findAllByReceiverOrderByCreatedAtDesc(currentUser);
    }

    public void updateNotification(Notification notification){
        notificationRepository.save(notificationRepository.getById(notification.getId()));
    }

    public void deleteNotification(long id) {
        notificationRepository.deleteById(id);
    }

    public void readNotification(long id) {
        Notification notification = notificationRepository.getById(id);
        if(!notification.isRead()) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    public void addNotificationBefriend(long id) {
        User sender = userService.getUserByUsername(UserService.getLoggedUsername());
        User befriended = userService.getUserById(id);
        notificationRepository.save(new Notification(
                "Friend request from " + sender.getUsername(),
                new Date(),
                false,
                befriended,
                sender));
        notificationRepository.save(new Notification(
                "You have accepted a friend request from " + befriended.getUsername(),
                new Date(),
                false,
                sender,
                sender
                ));
    }

    public void addNotificationUnfriend(long id) {
        User sender = userService.getUserByUsername(UserService.getLoggedUsername());
        User unfriended = userService.getUserById(id);
        notificationRepository.save(new Notification(
                sender.getUsername() + " removed you from his friends list",
                new Date(),
                false,
                unfriended,
                sender));
        notificationRepository.save(new Notification(
                "you have removed " + sender.getUsername() + " from your friends list",
                new Date(),
                false,
                sender,
                unfriended));
    }

    public void addNotificationRefuse(long id) {
        User sender = userService.getUserByUsername(UserService.getLoggedUsername());
        User refused = userService.getUserById(id);
        notificationRepository.save(new Notification(
                sender.getUsername() + " refuse your friend request",
                new Date(),
                false,
                refused,
                sender));
    }

    public void ihaveCovidNotification() {
        User coviduser = userService.getUserByUsername(UserService.getLoggedUsername());
        Date covidstart = java.sql.Date.valueOf(LocalDate.now().minusDays(DAYS_COVID));
        Date now = new Date();
        List<Activity> myactivites = activityService.findAllByUserAndStartBetween(coviduser, covidstart, now);
        List<User> friendsAtRisk = friendService.listFriends();
        List<User> usersAtRisk = new ArrayList<>();
        for (Activity activity: myactivites) {
            List<Activity> activitiesAtLocation = activityService.findAllByLocationAndUserNotAndStartBetween(activity.getLocation(), coviduser, covidstart, now);
            usersAtRisk = Stream.concat(usersAtRisk.stream(), activitiesAtLocation.stream().map(Activity::getUser)).collect(Collectors.<User>toList());
        }
        System.out.println("Users at risk : " + usersAtRisk);
        for (User userAtRisk: usersAtRisk) {
            System.out.println(userAtRisk.getUsername());
        }
        for (User friend: friendsAtRisk) {
            notificationRepository.save(new Notification(
                    "Your friend " + coviduser.getUsername() + " contracted Covid. Be careful !",
                    new Date(),
                    false,
                    friend,
                    coviduser
            ));
        }

        for (User userAtRisk: usersAtRisk) {
            notificationRepository.save(new Notification(
                    "Someone called " + coviduser.getUsername() + " contracted Covid recently while you went to at least one of the same place of his activities during that time. Be careful !",
                    new Date(),
                    false,
                    userAtRisk,
                    coviduser
            ));
        }
    }

    public void deleteAllByReceiverAndSender(User user1, User user2) {
        notificationRepository.deleteAllByReceiverAndSender(user1, user2);
    }

}