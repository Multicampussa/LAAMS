package multicampussa.laams.home.chat.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.chat.domain.ChatMessage;
import multicampussa.laams.home.chat.dto.SaveMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MongoTemplate mongoTemplate;

    public void saveMessage(ChatMessage chatMessage) {
        SaveMessage saveMessage = new SaveMessage();
        saveMessage.updateMessage(chatMessage);
        mongoTemplate.save(saveMessage);
    }

    public List<SaveMessage> getMessages(String roomId) {
        Query query = new Query(Criteria.where("roomId").is(roomId));
        List<SaveMessage> chatMessages = mongoTemplate.find(query, SaveMessage.class);
        List<SaveMessage> result = new ArrayList<>();
        for (SaveMessage chatMessage: chatMessages) {
            if (chatMessage.getDateTime().toLocalDate().equals(LocalDate.now())) {
                result.add(chatMessage);
            }
        }
        return result;
    }
}
