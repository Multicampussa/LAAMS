package multicampussa.laams.home.chat.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.dto.CreateChatRoomDto;
import multicampussa.laams.home.chat.service.ChatService;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
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
        ResponseEntity<String> result = chatService.createRoom(createChatRoomDto);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", result.getBody());
        resultMap.put("code", result.getStatusCode().value());
        resultMap.put("status", "success");
        System.out.println(result.get);
        return new ResponseEntity<>(resultMap, result.getStatusCode());
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomName) {
        return chatService.findByRoomName(roomName);
    }
}