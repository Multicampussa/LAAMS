package multicampussa.laams.home.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import multicampussa.laams.director.domain.Director.Director;
import multicampussa.laams.manager.domain.manager.Manager;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    public String id;

    public static MemberInfoDto fromEntityByDirector(Director director) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(director.getId());
        return memberInfoDto;
    }
    public static MemberInfoDto fromEntityByManager(Manager manager) {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setId(manager.getId());
        return memberInfoDto;
    }
}
