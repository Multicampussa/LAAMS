package multicampussa.laams.home.chat.repository;

import multicampussa.laams.home.chat.domain.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByRoomName(String roomName);
    boolean existsByRoomName(String roomName);
}
