package com.example.petlog.repository;

import com.example.petlog.entity.Pet;
import com.example.petlog.entity.User;
import com.example.petlog.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    List<UserNotification> findAllByReceiver(User user);
    List<UserNotification> findAllByReceiverOrderByCreatedAtDesc(User user);
}
