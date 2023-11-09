package multicampussa.laams.home.chat.repository;

import multicampussa.laams.home.chat.domain.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByRoomName(String roomName);
    boolean existsByRoomName(String roomName);
    List<ChatRoom> findByRoomNameContaining(String roomName);
}
