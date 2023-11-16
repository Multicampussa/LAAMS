package multicampussa.laams.home.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "chatRoom")
public class PrivateChatRoom extends ChatRoom {

    private String directorName;

    public static PrivateChatRoom create(String name) {
        PrivateChatRoom room = new PrivateChatRoom();
        room.setRoomId(UUID.randomUUID().toString());
        room.setRoomName(name);
        room.setRoomType("private");
        return room;
    }

    public void update(ChatRoom chatRoom) {
        this.setRoomId(chatRoom.getRoomId());
        this.setRoomType(chatRoom.getRoomType());
        this.setRoomName(chatRoom.getRoomName());
    }
}
