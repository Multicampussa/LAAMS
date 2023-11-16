package multicampussa.laams.home.member.dto;

import lombok.*;
import multicampussa.laams.director.domain.director.Director;

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

    public MemberUpdateDto(Director director) {
        this.name = director.getName();
        this.phone = director.getPhone();
    }
}
