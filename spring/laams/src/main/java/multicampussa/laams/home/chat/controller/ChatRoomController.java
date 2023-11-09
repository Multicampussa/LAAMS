package multicampussa.laams.home.chat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.dto.CreateChatRoomDto;
import multicampussa.laams.home.chat.service.ChatService;
import multicampussa.laams.home.member.dto.MemberSignUpDto;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.home.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
@Api(tags = "채팅방 관련 기능")
public class ChatRoomController {

    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    @ApiOperation(value = "채팅방 리스트")
    public List<ChatRoom> room(@RequestParam(required = false) String directorId, @RequestParam(required = false) String centerName, @RequestParam(defaultValue = "false") boolean isNow, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        String authority = jwtTokenProvider.getAuthority(token);
        String id = jwtTokenProvider.getId(token);

        List<ChatRoom> result = new ArrayList<>();
        if (authority.equals("ROLE_DIRECTOR")) {
            String regionByDirector = chatService.findRegionByDirector(id);
            String centerNameByDirector = chatService.findCenterNameByDirector(id);
            result.add(chatService.findByPrivateRoomName(id));
            result.add(chatService.findByRoomName("Notice"));
            result.add(chatService.findByRoomName(regionByDirector));
            result.add(chatService.findByRoomName(centerNameByDirector));
            if (chatService.isTesting(id)) {
                result.add(chatService.findByRoomName("Now"));
            }
            return result;
        } else if (authority.equals("ROLE_CENTER_MANAGER")) {
            return result;
        }

        return chatService.findSearchRoom(directorId, centerName, isNow);
    }

    // 일대일 채팅방 생성
    @PostMapping("/room")
    @ApiOperation(value = "일대일 채팅방 생성")
    public ResponseEntity<Map<String, Object>> createRooms(@ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        String directorId = jwtTokenProvider.getId(token);

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("data", chatService.createRoom(directorId));
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("code", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    // 전체 공지 채팅방 생성
    @PostMapping("/room/notice")
    @ApiOperation(value = "모든 공지 채팅방 생성")
    public ResponseEntity<Map<String, Object>> createNoticeRoom() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("notice-all", chatService.createNoticeRoom());
            resultMap.put("noticeByRegion", chatService.createNoticeRoomByRegion());
            resultMap.put("noticeByCenter", chatService.createNoticeRoomByCenter());
            resultMap.put("noticeForNow", chatService.createNoticeRoomForNow());
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("code", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

//    // 지역별 공지 채팅방 생성
//    @PostMapping("/room/notice/region")
//    @ApiOperation(value = "지역별 공지 채팅방 생성")
//    public ResponseEntity<Map<String, Object>> createNoticeRoomByRegion() {
//        Map<String, Object> resultMap = new HashMap<>();
//        try {
//            resultMap.put("data", chatService.createNoticeRoomByRegion());
//            resultMap.put("code", HttpStatus.OK.value());
//            resultMap.put("status", "success");
//            return new ResponseEntity<>(resultMap, HttpStatus.OK);
//        } catch (Exception e) {
//            resultMap.put("message", e.getMessage());
//            resultMap.put("code", HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 센터별 공지 채팅방 생성
//    @PostMapping("/room/notice/center")
//    @ApiOperation(value = "센터별 공지 채팅방 생성")
//    public ResponseEntity<Map<String, Object>> createNoticeRoomByCenter() {
//        Map<String, Object> resultMap = new HashMap<>();
//        try {
//            resultMap.put("data", chatService.createNoticeRoomByCenter());
//            resultMap.put("code", HttpStatus.OK.value());
//            resultMap.put("status", "success");
//            return new ResponseEntity<>(resultMap, HttpStatus.OK);
//        } catch (Exception e) {
//            resultMap.put("message", e.getMessage());
//            resultMap.put("code", HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 현재 진행중인 시험 공지 채팅방 생성
//    @PostMapping("/room/notice/now")
//    @ApiOperation(value = "현재 진행중인 시험 공지 채팅방 생성")
//    public ResponseEntity<Map<String, Object>> createNoticeRoomForNow() {
//        Map<String, Object> resultMap = new HashMap<>();
//        try {
//            resultMap.put("data", chatService.createNoticeRoomForNow());
//            resultMap.put("code", HttpStatus.OK.value());
//            resultMap.put("status", "success");
//            return new ResponseEntity<>(resultMap, HttpStatus.OK);
//        } catch (Exception e) {
//            resultMap.put("message", e.getMessage());
//            resultMap.put("code", HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
//        }
//    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
}
