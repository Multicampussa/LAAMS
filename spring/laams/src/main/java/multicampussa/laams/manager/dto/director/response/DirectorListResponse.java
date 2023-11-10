package multicampussa.laams.manager.dto.director.response;

import lombok.Getter;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.manager.domain.exam.ExamDirector;

@Getter
public class DirectorListResponse {
    private String directorName;
    private String directorPhoneNum;
    private Boolean directorAttendance;

    public DirectorListResponse(Director director, ExamDirector examDirector) {
        this.directorName = director.getName();
        this.directorPhoneNum = director.getPhone();
        this.directorAttendance = examDirector.getDirectorAttendance();
    }
}
