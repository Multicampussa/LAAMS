package multicampussa.laams.manager.dto.examinee.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;

@Getter
public class ExamineeCreateRequest {

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private int age;

    @ApiModelProperty
    private String phoneNum;

    @ApiModelProperty
    private String gender;

    @ApiModelProperty
    private String id;

    @ApiModelProperty
    private String pw;

    @ApiModelProperty
    private String email;
}
