package multicampussa.laams.home.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdatePasswordDto {
    private String id;
    private String oldPassword;
    private String newPassword;
}
