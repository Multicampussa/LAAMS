package multicampussa.laams.home.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomDto {
    private String receiverId;
}