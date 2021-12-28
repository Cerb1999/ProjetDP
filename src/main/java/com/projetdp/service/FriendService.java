package com.projetdp.service;

import com.projetdp.model.Friend;
import com.projetdp.model.FriendState;
import com.projetdp.model.User;
import com.projetdp.repository.FriendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserService userService;

    public boolean addFriendRequest(long friendId) {
        User user1 = userService.getUserByUsername(UserService.getLoggedUsername());
        boolean ok = friendId != user1.getId();

        if(ok) {
            User user2 = userService.getUserById(friendId);

            if (!friendRepository.existsByFirstUserAndSecondUser(user1, user2) && !friendRepository.existsByFirstUserAndSecondUser(user2, user1)) {
                friendRepository.save(new Friend(new Date(), user1, user2, FriendState.PENDING));
            } else ok = false;
        }
        return ok;
    }

    public boolean addFriend(long friendId) {
        User user1 = userService.getUserByUsername(UserService.getLoggedUsername());
        boolean isSame = friendId == user1.getId();
        if(!isSame) {
            User user2 = userService.getUserById(friendId);
            Friend friend = friendRepository.findByFirstUserAndSecondUser(user2, user1);
            friend.setState(FriendState.ACCEPTED);
            friendRepository.save(friend);
        }
        return !isSame;
    }

    public boolean refuseFriend(long friendId) {
        User user1 = userService.getUserByUsername(UserService.getLoggedUsername());
        boolean isSame = friendId == user1.getId();

        if(!isSame) {
            User user2 = userService.getUserById(friendId);
            Friend friend = friendRepository.findByFirstUserAndSecondUser(user2, user1);
            friend.setState(FriendState.REJECTED);
            friendRepository.save(friend);
        }
        return !isSame;
    }

    public boolean removeFriend(long friendId) {
        User user1 = userService.getUserByUsername(UserService.getLoggedUsername());
        boolean isSame = friendId == user1.getId();
        System.out.println("ISAME" + isSame);
        if(!isSame) {
            User user2 = userService.getUserById(friendId);
            if (friendRepository.existsByFirstUserAndSecondUser(user1, user2) || friendRepository.existsByFirstUserAndSecondUser(user2, user1)) {
                //Friend friend = friendRepository.findByFirstUserAndSecondUserOrSecondUserAndFirstUser(user1, user2, user2, user1);
                Friend friend = friendRepository.findByFirstUserAndSecondUser(user1, user2);
                System.out.println("FRIEND : " + friend);
                if(friend == null){
                    friend = friendRepository.findByFirstUserAndSecondUser(user2, user1);
                }
                friendRepository.delete(friend);
                System.out.println("FRIEND : " + friend);

                //notificationService.deleteAllByReceiverAndSender(user1, user2);
            }
        }
        return !isSame;
    }

    public List<User> listFriendsByState(FriendState state){
        User currentUser = userService.getUserByUsername(UserService.getLoggedUsername());
        List<User> friendUsers = new ArrayList<>();

        if(!state.equals(FriendState.PENDING)) {
            List<Friend> friendsByFirstUser = friendRepository.findByFirstUserAndStateEquals(currentUser, state);
            for (Friend friend : friendsByFirstUser) {
                friendUsers.add(userService.getUserById(friend.getSecondUser().getId()));
            }
        }

        List<Friend> friendsBySecondUser = friendRepository.findBySecondUserAndStateEquals(currentUser, state);
        for (Friend friend : friendsBySecondUser) {
            friendUsers.add(userService.getUserById(friend.getFirstUser().getId()));
        }

        return friendUsers;
    }


    public List<User> listFriendRequests() {
        return listFriendsByState(FriendState.PENDING);
    }

    public List<User> listFriends() {
        return listFriendsByState(FriendState.ACCEPTED);
    }

    public List<User> rejectedFriends() {
        return listFriendsByState(FriendState.REJECTED);
    }
}
