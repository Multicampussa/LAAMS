package multicampussa.laams.home.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpDto {
    private String id;
    private String email;
    private String name;
    private String pw;
    private String phone;
    private String address;

//    public MemberSignUpDto(Member member) {
//        this.email = member.getEmail();
//        this.memberName = member.getMemberName();
//        this.phoneNumber = member.getPhoneNumber();
//        this.password = member.getPassword();
//    }
}