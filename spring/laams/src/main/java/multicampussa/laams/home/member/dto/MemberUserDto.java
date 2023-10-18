package multicampussa.laams.home.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import multicampussa.laams.director.domain.Director;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUserDto {
    private String email;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;

    public MemberUserDto(Director director) {
        this.email = director.getEmail();
        this.name = director.getName();
        this.phone = director.getPhone();
        this.createdAt = director.getCreatedAt();
        this.updatedAt = director.getUpdatedAt();
    }
}