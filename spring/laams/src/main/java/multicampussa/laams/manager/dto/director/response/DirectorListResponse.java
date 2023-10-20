package multicampussa.laams.manager.dto.director.response;

import lombok.Getter;
import multicampussa.laams.director.domain.Director;

@Getter
public class DirectorListResponse {
    private String directorName;
    private String directorPhoneNum;

    public DirectorListResponse(Director director) {
        this.directorName = director.getName();
        this.directorPhoneNum = director.getPhone();
    }
}
