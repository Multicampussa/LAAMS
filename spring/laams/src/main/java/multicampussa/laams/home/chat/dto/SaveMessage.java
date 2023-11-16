package multicampussa.laams.home.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import multicampussa.laams.home.chat.domain.ChatMessage;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message")
public class SaveMessage {
    public void updateMessage(ChatMessage chatMessage) {
        this.type = chatMessage.getType();
        this.message = chatMessage.getMessage();
        this.roomId = chatMessage.getRoomId();
        this.roomName = chatMessage.getRoomName();
        this.sender = chatMessage.getSender();
        this.dateTime = LocalDateTime.now();
    }

    private ChatMessage.MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
    //채팅방 이름
    private String roomName;
    //보내는 시간
    private LocalDateTime dateTime;
}
