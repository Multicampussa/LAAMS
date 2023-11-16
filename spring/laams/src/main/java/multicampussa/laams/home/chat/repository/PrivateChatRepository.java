package multicampussa.laams.home.chat.repository;

import multicampussa.laams.home.chat.domain.ChatRoom;
import multicampussa.laams.home.chat.domain.PrivateChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PrivateChatRepository extends MongoRepository<PrivateChatRoom, String> {
    PrivateChatRoom findByRoomName(String roomName);
    boolean existsByRoomName(String roomName);
    PrivateChatRoom findByRoomNameContaining(String roomName);
}
