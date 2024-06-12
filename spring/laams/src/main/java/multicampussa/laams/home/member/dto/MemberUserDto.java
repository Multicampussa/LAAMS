package multicampussa.laams.home.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import multicampussa.laams.home.member.domain.Member;

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

    public MemberUserDto(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
    }
}