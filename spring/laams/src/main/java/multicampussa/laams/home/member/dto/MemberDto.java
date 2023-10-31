package multicampussa.laams.home.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.manager.domain.manager.Manager;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long memberNo;

    private String email;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;

    @Builder
    public MemberDto(Director director) {
        this.memberNo = director.getNo();
        this.email = director.getEmail();
        this.name = director.getName();
        this.phone = director.getPhone();
        this.createdAt = director.getCreatedAt();
        this.updatedAt = director.getUpdatedAt();
    }

    public static MemberDto fromEntityByDirector(Director director) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(director.getEmail());
        memberDto.setName(director.getName());
        memberDto.setPhone(director.getPhone());
        return memberDto;
    }

    public static MemberDto fromEntityByManager(Manager manager) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(manager.getName());
        memberDto.setPhone(manager.getPhone());
        return memberDto;
    }
}

