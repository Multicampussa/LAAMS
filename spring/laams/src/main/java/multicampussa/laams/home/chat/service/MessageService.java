package multicampussa.laams.home.chat.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.chat.domain.ChatMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MongoTemplate mongoTemplate;

    public void saveMessage(ChatMessage chatMessage) {
        mongoTemplate.save(chatMessage);
    }

    public List<ChatMessage> getMessages(String roomId) {
        Query query = new Query(Criteria.where("roomId").is(roomId));
        return mongoTemplate.find(query, ChatMessage.class);
    }
}
