package multicampussa.laams.home.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import multicampussa.laams.director.domain.Director;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long memberId;

    private String email;
    private String memberName;
    private String phoneNumber;
    private Boolean isDelete;
    private String verificationCode;
    private Boolean isVerified;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;

    @Builder
    public MemberDto(Director director) {
        this.memberId = director.getNo();
        this.email = director.getEmail();
        this.memberName = director.getMemberName();
        this.phoneNumber = director.getPhoneNumber();
        this.isDelete = director.getIsDelete();
        this.createdAt = director.getCreatedAt();
        this.updatedAt = director.getUpdatedAt();
        this.verificationCode = director.getVerificationCode();
        this.isVerified = director.getIsVerified();
    }
}

