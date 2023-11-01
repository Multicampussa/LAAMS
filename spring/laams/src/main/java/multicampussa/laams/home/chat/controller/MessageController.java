package multicampussa.laams.home.chat.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.chat.domain.ChatMessage;
import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.service.MessageService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
//        System.out.println(headers);
//        String authorization = (String) headers.get("authorization");
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            throw new IllegalArgumentException("토큰이 올바르지 않습니다.");
//        }

//        String token = authorization.replace("Bearer ", "");
//        String id = jwtTokenProvider.getId(token);
//        message.setSender(id);
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        System.out.println(message.getMessage());
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message);
        messageService.saveMessage(message);
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public List<ChatMessage> roomInfo(@PathVariable String roomId) {
        return messageService.getMessages(roomId);
    }
}
