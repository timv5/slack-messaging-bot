package com.learning.slack.slackmessagingbot.service.dao;

import com.learning.slack.slackmessagingbot.dto.MessageRequest;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;

import java.io.IOException;
import java.util.List;

public interface MessageService {

    void sendChannelMessage(MessageRequest message);

    void sendUserMessage(MessageRequest request) throws SlackApiException, IOException;

    List<Conversation> findConversations() throws SlackApiException, IOException;

    boolean scheduleUserMessage(MessageRequest request) throws SlackApiException, IOException;

    boolean scheduleChannelMessage(MessageRequest request) throws SlackApiException, IOException;

}
