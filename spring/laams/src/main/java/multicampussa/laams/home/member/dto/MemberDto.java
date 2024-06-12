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
public class MemberDto {
    private Long memberNo;
    private String id;
    private String email;
    private String name;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;
    private String role;

    @Builder
    public MemberDto(Member member) {
        this.memberNo = member.getNo();
        this.email = member.getEmail();
        this.name = member.getName();
        this.id = member.getId();
        this.phone = member.getPhone();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
        this.role = member.getRole();
    }

    public static MemberDto fromEntityByDirector(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setPhone(member.getPhone());
        return memberDto;
    }

    public static MemberDto fromEntityByManager(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(member.getName());
        memberDto.setPhone(member.getPhone());
        return memberDto;
    }
}

