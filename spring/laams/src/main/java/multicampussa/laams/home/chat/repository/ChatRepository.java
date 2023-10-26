package multicampussa.laams.home.chat.repository;

import multicampussa.laams.director.domain.Director;
import multicampussa.laams.home.chat.domain.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByRoomId(String roomId);
}