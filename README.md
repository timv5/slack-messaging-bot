# slack-messaging-bot
Bot that sends messages to slack.

## Slack documentation
https://api.slack.com/

## Technologies used
- java 17
- slack app

## Instructions
1. create slack bot
2. set permissions: read, write, chat
3. get BOT TOKEN and set it in Constants.java

## Endpoint
- POST http://localhost:8080/api/message/send body: {"content": "test"}
- POST http://localhost:8080/api/message/user/send body: {"content": "Test message","channel": "PUT_YOUR_USER_CHANNEL_ID_HERE"}
- POST http://localhost:8080/api/message/channel/send body: {"content": "Test message","channel": "general"}
- GET http://localhost:8080/api/message/channel/list
- POST http://localhost:8080/api/message/channel/schedule body: {"content": "scheduled message","channel": "general","sendDate": 1684142656}
- POST http://localhost:8080/api/message/user/schedule body: {"content": "scheduled message","channel": "PUT_YOUR_USER_CHANNEL_ID_HERE","sendDate": 1684142656}