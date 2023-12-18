package multicampussa.laams.home.member.dto;

import lombok.*;
import multicampussa.laams.home.member.domain.Member;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDto {
    private String id;
    private String name;
    private String phone;
    private String email;

    public MemberUpdateDto(Member member) {
        this.name = member.getName();
        this.phone = member.getPhone();
    }
}
