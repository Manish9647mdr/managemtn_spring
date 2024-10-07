package com.tkk.projectmgtsystem.repository;

import com.tkk.projectmgtsystem.modal.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
