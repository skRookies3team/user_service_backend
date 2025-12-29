package com.example.petlog.repository;

import com.example.petlog.entity.Archive;
import com.example.petlog.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
