package multicampussa.laams.home.chat.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.dto.CreateChatRoomDto;
import multicampussa.laams.home.chat.service.ChatService;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ApiOperation(value = "채팅방 생성")
    public ResponseEntity<Map<String, Object>> createRoom(@RequestBody CreateChatRoomDto createChatRoomDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ResponseEntity<String> result = chatService.createRoom(createChatRoomDto);
            resultMap.put("message", result.getBody());
            resultMap.put("code", result.getStatusCode().value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, result.getStatusCode());
        } catch (Exception e) {
            resultMap.put("message", e.getMessage().split(":")[1]);
            resultMap.put("code", e.getMessage().split(":")[0]);
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
}