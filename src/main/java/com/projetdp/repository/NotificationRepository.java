package com.projetdp.repository;

import com.projetdp.model.Notification;
import com.projetdp.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    Notification getById(long id);
    Notification findByReceiver(User user);
    Notification findByReceiverAndId(User user, long id);
    List<Notification> findAllByReceiverOrderByCreatedAtDesc(User user);
    List<Notification> findAllByReceiverAndSenderNotOrderByCreatedAtDesc(User user, User alsosender);
    void deleteById(long id);
    void deleteAllByReceiverAndSender(User receiver, User sender);
}