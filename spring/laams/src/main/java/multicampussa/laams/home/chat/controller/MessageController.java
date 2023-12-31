package multicampussa.laams.home.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.chat.domain.ChatMessage;
import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.dto.SaveMessage;
import multicampussa.laams.home.chat.service.ChatService;
import multicampussa.laams.home.chat.service.MessageService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.service.center.CenterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Api(tags = "채팅 메시지 관련 기능")
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CenterService centerService;

    @MessageMapping("/chat/message")
    @ApiOperation(value = "일대일 채팅방 입장 및 메시지 전송")
    public ResponseEntity<Map<String, Object>> enter(ChatMessage message, @Header("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String id = jwtTokenProvider.getId(token);
        String authority = jwtTokenProvider.getAuthority(token);
        Map<String, Object> resultMap = new HashMap<>();
        if (authority.equals("ROLE_DIRECTOR")) {
            if (!message.getSender().equals(id)) {
                resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
                resultMap.put("message", "권한이 없습니다.");
                return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
            }
        } else if (authority.equals("ROLE_MANAGER")) {
            message.setSender("운영자");
        } else {
            resultMap.put("message", "권한이 없습니다.");
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(id+"님이 입장하였습니다.");
        }
        System.out.println(message.getMessage());
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message);
        if (authority.equals("ROLE_DIRECTOR")) {
            sendingOperations.convertAndSend("/topic/chat/room/alarm", message);
        }
        messageService.saveMessage(message);
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("message", "성공적으로 전송되었습니다.");
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @MessageMapping("/chat/message/notice")
    @ApiOperation(value = "전체 채팅방 입장 및 메시지 전송")
    public ResponseEntity<Map<String, Object>> noticeEnter(ChatMessage message, @Header("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String id = jwtTokenProvider.getId(token);
        String authority = jwtTokenProvider.getAuthority(token);
        Map<String, Object> resultMap = new HashMap<>();
        if (authority.equals("ROLE_DIRECTOR")) {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        } else if (authority.equals("ROLE_MANAGER")) {
            message.setSender("운영자");
        } else {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(id+"님이 입장하였습니다.");
        }
        System.out.println(message.getMessage());
        sendingOperations.convertAndSend("/topic/chat/room/notice-all", message);
        messageService.saveMessage(message);
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("message", "성공적으로 전송되었습니다.");
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @MessageMapping("/chat/message/notice/region")
    @ApiOperation(value = "지역별 채팅방 입장 및 메시지 전송")
    public ResponseEntity<Map<String, Object>> noticeEnterByRegion(ChatMessage message, @Header("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String id = jwtTokenProvider.getId(token);
        String authority = jwtTokenProvider.getAuthority(token);
        String region = message.getRoomName();

        Map<String, Object> resultMap = new HashMap<>();
        if (authority.equals("ROLE_DIRECTOR")) {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        } else if (authority.equals("ROLE_MANAGER")) {
            message.setSender("운영자");
        } else {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(id+"님이 입장하였습니다.");
        }
        System.out.println(message.getMessage());
        sendingOperations.convertAndSend("/topic/chat/room/notice-" + region, message);
        messageService.saveMessage(message);
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("message", "성공적으로 전송되었습니다.");
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @MessageMapping("/chat/message/notice/center")
    @ApiOperation(value = "센터별 채팅방 입장 및 메시지 전송")
    public ResponseEntity<Map<String, Object>> noticeEnterByCenter(ChatMessage message, @Header("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String id = jwtTokenProvider.getId(token);
        String authority = jwtTokenProvider.getAuthority(token);

        Map<String, Object> resultMap = new HashMap<>();
        if (authority.equals("ROLE_DIRECTOR")) {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        } else if (authority.equals("ROLE_MANAGER")) {
            message.setSender("운영자");
        } else {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(id+"님이 입장하였습니다.");
        }
        System.out.println(message.getMessage());
        sendingOperations.convertAndSend("/topic/chat/room/notice-" + message.getRoomName(), message);
        messageService.saveMessage(message);
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("message", "성공적으로 전송되었습니다.");
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @MessageMapping("/chat/message/notice/now")
    @ApiOperation(value = "현재 진행중인 시험 공지 채팅방 입장 및 메시지 전송")
    public ResponseEntity<Map<String, Object>> noticeEnterForNow(ChatMessage message, @Header("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String id = jwtTokenProvider.getId(token);
        String authority = jwtTokenProvider.getAuthority(token);

        Map<String, Object> resultMap = new HashMap<>();
        if (authority.equals("ROLE_DIRECTOR")) {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        } else if (authority.equals("ROLE_MANAGER")) {
            message.setSender("운영자");
        } else {
            resultMap.put("code", HttpStatus.UNAUTHORIZED.value());
            resultMap.put("message", "권한이 없습니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(id+"님이 입장하였습니다.");
        }
        System.out.println(message.getMessage());
        sendingOperations.convertAndSend("/topic/chat/room/notice-now", message);
        messageService.saveMessage(message);
        resultMap.put("code", HttpStatus.OK.value());
        resultMap.put("message", "성공적으로 전송되었습니다.");
        resultMap.put("status", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    @ApiOperation(value = "채팅 내역 조회")
    public List<SaveMessage> roomInfo(@PathVariable String roomId) {
        return messageService.getMessages(roomId);
    }
}
