package com.tkk.projectmgtsystem.service;

import com.tkk.projectmgtsystem.modal.Chat;
import com.tkk.projectmgtsystem.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
