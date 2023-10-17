package multicampussa.laams.home.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationConfirmationDto {
    private String email;
    private String code;
}
