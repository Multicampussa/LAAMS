package multicampussa.laams.manager.dto.director.response;

import lombok.Getter;
import multicampussa.laams.home.member.domain.Member;
import multicampussa.laams.manager.domain.exam.ExamDirector;

@Getter
public class DirectorListResponse {
    private String directorName;
    private String directorPhoneNum;
    private Boolean directorAttendance;

    public DirectorListResponse(Member member, ExamDirector examDirector) {
        this.directorName = member.getName();
        this.directorPhoneNum = member.getPhone();
        this.directorAttendance = examDirector.getDirectorAttendance();
    }
}
