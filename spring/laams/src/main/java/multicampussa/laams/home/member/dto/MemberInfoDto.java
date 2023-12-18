package multicampussa.laams.home.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import multicampussa.laams.centerManager.domain.CenterManager;
import multicampussa.laams.home.member.domain.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    public String id;

    public static MemberInfoDto fromEntityByDirector(Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(member.getId());
        return memberInfoDto;
    }
    public static MemberInfoDto fromEntityByManager(Member member) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(member.getId());
        return memberInfoDto;
    }
    public static MemberInfoDto fromEntityByCenterManager(CenterManager centerManager) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(centerManager.getId());
        return memberInfoDto;
    }
}
