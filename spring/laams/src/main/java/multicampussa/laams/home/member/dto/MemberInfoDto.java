package multicampussa.laams.home.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import multicampussa.laams.home.member.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    public String id;

    public static MemberInfoDto fromEntityByDirector(multicampussa.laams.home.member.domain.Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(member.getId());
        return memberInfoDto;
    }
    public static MemberInfoDto fromEntityByManager(multicampussa.laams.home.member.domain.Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(member.getId());
        return memberInfoDto;
    }
    public static MemberInfoDto fromEntityByCenterManager(Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(member.getId());
        return memberInfoDto;
    }
}
