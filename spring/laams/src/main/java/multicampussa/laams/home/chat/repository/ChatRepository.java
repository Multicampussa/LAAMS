package multicampussa.laams.home.chat.repository;

import multicampussa.laams.home.chat.domain.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChatRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByRoomName(String roomName);
    boolean existsByRoomName(String roomName);
    ChatRoom findByRoomNameContaining(String roomName);
}
