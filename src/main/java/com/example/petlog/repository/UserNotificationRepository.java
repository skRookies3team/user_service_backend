package com.example.petlog.repository;

import com.example.petlog.entity.Pet;
import com.example.petlog.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
}
