package multicampussa.laams.home.chat.repository;

import multicampussa.laams.home.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<ChatMessage, String> {
    ChatMessage findByRoomId(String roomId);
    boolean existsByRoomId(String roomId);
}