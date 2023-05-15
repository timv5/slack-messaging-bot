package com.learning.slack.slackmessagingbot.service.impl;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.learning.slack.slackmessagingbot.dto.MessageRequest;
import com.learning.slack.slackmessagingbot.service.dao.MessageService;
import com.learning.slack.slackmessagingbot.utils.Constants;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatScheduleMessageRequest;
import com.slack.api.methods.request.conversations.ConversationsListRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatScheduleMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void sendChannelMessage(MessageRequest request) {
        SlackClientRuntimeConfig runtimeConfig = SlackClientRuntimeConfig.builder()
                .setTokenSupplier(() -> Constants.BOT_TOKEN).build();
        SlackClient slackClient = SlackClientFactory.defaultFactory().build(runtimeConfig);
        slackClient.postMessage(
                ChatPostMessageParams.builder()
                        .setAsUser(true)
                        .setText(request.content())
                        .setChannelId(request.channel())
                        .build()
        ).join().isOk();
    }

    @Override
    public void sendUserMessage(MessageRequest request) throws SlackApiException, IOException {
        MethodsClient client = Slack.getInstance().methods(Constants.BOT_TOKEN);
        ChatPostMessageResponse response = client.chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(Constants.BOT_TOKEN)
                        .channel(request.channel())
                        .text(request.content())
                        .build()
        );

        LOGGER.info(response.toString());
    }

    @Override
    public List<Conversation> findConversations() throws SlackApiException, IOException {
        MethodsClient client = Slack.getInstance().methods(Constants.BOT_TOKEN);
        ConversationsListResponse response = client.conversationsList(ConversationsListRequest.builder().token(Constants.BOT_TOKEN).build());
        if (response.isOk()) {
            return response.getChannels();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean scheduleUserMessage(MessageRequest request) throws SlackApiException, IOException {
        MethodsClient client = Slack.getInstance().methods(Constants.BOT_TOKEN);
        ChatScheduleMessageResponse response = client.chatScheduleMessage(ChatScheduleMessageRequest.builder()
                        .channel(request.channel())
                        .asUser(true)
                        .postAt(request.sendDate())
                        .text(request.content())
                .build());
        if (response.isOk()) {
            return true;
        } else {
            LOGGER.error(response.getError());
            return false;
        }
    }

    @Override
    public boolean scheduleChannelMessage(MessageRequest request) throws SlackApiException, IOException {
        MethodsClient client = Slack.getInstance().methods(Constants.BOT_TOKEN);
        ChatScheduleMessageResponse response = client.chatScheduleMessage(ChatScheduleMessageRequest.builder()
                .channel(request.channel())
                .postAt(request.sendDate())
                .text(request.content())
                .build());
        if (response.isOk()) {
            return true;
        } else {
            LOGGER.error(response.getError());
            return false;
        }
    }
}
