package com.projetdp.repository;

import com.projetdp.model.Friend;
import com.projetdp.model.FriendState;
import com.projetdp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FriendRepository extends JpaRepository<Friend,Integer> {
    boolean existsByFirstUserAndSecondUser(User first, User second);
    List<Friend> findByFirstUserAndStateEquals(User user, FriendState state);
    List<Friend> findBySecondUserAndStateEquals(User user, FriendState state);
    Friend findByFirstUserAndSecondUserOrSecondUserAndFirstUser(User user1, User user2, User second, User first);
    Friend findByFirstUserAndSecondUser(User user1, User user2);
}
