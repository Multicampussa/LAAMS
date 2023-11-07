package multicampussa.laams.home.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.dto.CreateChatRoomDto;
import multicampussa.laams.home.chat.repository.ChatRepository;
import multicampussa.laams.home.member.repository.MemberDirectorRepository;
import multicampussa.laams.home.member.repository.MemberManagerRepository;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private Map<String, ChatRoom> chatRooms;
    private final ChatRepository chatRepository;
    private final MemberDirectorRepository memberDirectorRepository;
    private final CenterRepository centerRepository;
    private final ExamRepository examRepository;

    @PostConstruct
    //의존관계 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoom> findSearchRoom(String region, String centerName) {
        List<ChatRoom> result = new ArrayList<>();
        if (centerName != null) {
            result.add(chatRepository.findByRoomName(centerName));
        } else if (region != null) {
            List<Center> centers = centerRepository.findByRegion(region);
            for (Center center : centers) {
                result.add(chatRepository.findByRoomName(center.getName()));
            }
        } else {
            result = chatRepository.findAll();
        }

        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findByRoomName(String roomName) {
        return chatRepository.findByRoomName(roomName);
    }

    // 채팅방 생성
    public ChatRoom createRoom(String directorId) {
        String roomName;
        List<ChatRoom> rooms = chatRepository.findAll();
        for (ChatRoom room : rooms) {
            if (room.getRoomName().contains(directorId)) {
                return room;
            };
        }
        if (memberDirectorRepository.existsById(directorId)) {
            roomName = directorId;
        } else {
            throw new IllegalArgumentException("해당 유저는 없습니다.");
        }

        ChatRoom chatRoom = ChatRoom.create(roomName);
        chatRepository.save(chatRoom);
        return chatRoom;
    }

    // 전체 공지 채팅방 생성
    public ChatRoom createNoticeRoom() {
        List<ChatRoom> rooms = chatRepository.findAll();
        for (ChatRoom room : rooms) {
            if (room.getRoomName().contains("Notice")) {
                return room;
            };
        }

        ChatRoom chatRoom = ChatRoom.create("Notice");
        chatRepository.save(chatRoom);
        return chatRoom;
    }

    // 지역별 공지 채팅방 생성
    public List<ChatRoom> createNoticeRoomByRegion() {
        List<Center> centers = centerRepository.findAll();

        Set<String> regionSet = new HashSet<>();
        for (Center center : centers) {
            regionSet.add(center.getRegion());
        }

        List<ChatRoom> chatRooms = new ArrayList<>();
        for (String region : regionSet) {
            if (chatRepository.existsByRoomName(region)) {
                chatRooms.add(chatRepository.findByRoomName(region));
            } else {
                ChatRoom chatRoom = ChatRoom.create(region);
                chatRepository.save(chatRoom);
                chatRooms.add(chatRoom);
            }
        }

        return chatRooms;
    }

    // 감독관 ID로 해당 감독관이 담당하는 지역 찾기
    public String findRegionByDirector(String id) {
        Center center = centerRepository.findByDirectorId(id);
        return center.getRegion();
    }

    // 센터별 공지 채팅방 생성
    public List<ChatRoom> createNoticeRoomByCenter() {
        List<Center> centers = centerRepository.findAll();

        List<ChatRoom> chatRooms = new ArrayList<>();
        for (Center center : centers) {
            if (chatRepository.existsByRoomName(center.getName())) {
                chatRooms.add(chatRepository.findByRoomName(center.getName()));
            } else {
                ChatRoom chatRoom = ChatRoom.create(center.getName());
                chatRepository.save(chatRoom);
                chatRooms.add(chatRoom);
            }
        }

        return chatRooms;
    }

    // 감독관 ID로 센터 이름 찾기
    public String findCenterNameByDirector(String id) {
        Center center = centerRepository.findByDirectorId(id);
        return center.getName();
    }

    // 현재 진행중인 시험 공지 채팅방 생성
    public List<ChatRoom> createNoticeRoomForNow() {
        List<ChatRoom> chatRooms = new ArrayList<>();
        if (!chatRepository.existsByRoomName("Now")) {
            ChatRoom chatRoom = ChatRoom.create("Now");
            chatRepository.save(chatRoom);
            chatRooms.add(chatRoom);
        } else {
            chatRooms.add(chatRepository.findByRoomName("Now"));
        }

        return chatRooms;
    }

    // 해당 감독관이 감독 진행중인 시험이 있는지
    public boolean isTesting(String id) {
        List<Exam> exams = examRepository.findByDirectorId(id);

        for (Exam exam : exams) {
            if (exam.getExamDate().isBefore(LocalDateTime.now()) &&
                    (exam.getExamDate().plusMinutes(exam.getRunningTime())).isAfter(LocalDateTime.now())) {
                return true;
            }
        }

        return false;
    }
}
