package multicampussa.laams.director.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.*;
import multicampussa.laams.director.service.DirectorService;
import multicampussa.laams.home.member.jwt.JwtTokenProvider;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/director")
@Api(tags = "감독관")
public class DirectorController {

    private final DirectorService directorService;
    private final JwtTokenProvider jwtTokenProvider;

    // 시험 목록 조회
//    @GetMapping("/{directorNo}/exams")
//    public List<ExamListDto> getExams(@PathVariable Long directorNo){
//        return directorService.getExamList(directorNo);
//    }

    @ApiOperation(value = "시험 월별 조회 및 일별 조회")
    @GetMapping("/{directorNo}/exams")
    public ResponseEntity<Map<String, Object>> getExamMonthDayList(@RequestHeader String authorization, @PathVariable Long directorNo, @RequestParam int year, @RequestParam int month, @RequestParam(value = "day", defaultValue = "0") int day){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);

            resultMap.put("message","시험을 성공적으로 조회했습니다.");
            resultMap.put("data", directorService.getExamMonthDayList(directorNo, year, month, day, authority));
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "시험 상세정보 조회")
    @GetMapping("/exams/{examNo}")
    public ResponseEntity<Map<String, Object>> getExamInformation(@RequestHeader String authorization, @PathVariable Long examNo){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);

            ExamInformationDto examInformationDto = directorService.getExamInformation(examNo, authority);

            resultMap.put("message","시험 상세정보를 성공적으로 조회했습니다.");
            resultMap.put("data", examInformationDto);
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "시험 응시자 목록 조회")
    @GetMapping("/exams/{examNo}/examinees")
    public ResponseEntity<Map<String, Object>> getExamExamineeList(@RequestHeader String authorization, @PathVariable Long examNo) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);

            resultMap.put("message","시험 응시자 목록을 성공적으로 조회했습니다.");
            resultMap.put("data", directorService.getExamExamineeList(examNo, authority));
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "시험 응시자 상세 조회")
    @GetMapping("/exams/{examNo}/examinees/{examineeNo}")
    public ResponseEntity<Map<String, Object>> getExamExaminee(@RequestHeader String authorization, @PathVariable Long examNo, @PathVariable Long examineeNo){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);

            ExamExamineeDto examExamineeDto = directorService.getExamExaminee(examNo, examineeNo, authority);

            resultMap.put("message","시험 응시자의 상세 정보를 성공적으로 조회했습니다.");
            resultMap.put("data", examExamineeDto);
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "시험 응시자 현황 조회")
    @GetMapping("/exams/{examNo}/status")
    public ResponseEntity<Map<String, Object>> getExamStatus(@RequestHeader String authorization, @PathVariable Long examNo){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String token  = authorization.replace("Bearer ", "");
            String authority = jwtTokenProvider.getAuthority(token);

            ExamStatusDto examStatusDto = directorService.getExamStatus(examNo, authority);

            resultMap.put("message","시험 응시자의 현황을 성공적으로 조회했습니다.");
            resultMap.put("data", examStatusDto);
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }
    
    @ApiOperation(value = "응시자 출석(응시자용) - 출석시간 업데이트")
    @PutMapping("/exams/{examNo}/examinees/{examineeNo}/attendanceTime")
    public ResponseEntity<Map<String, Object>> updateAttendanceTime(@PathVariable Long examNo, @PathVariable Long examineeNo){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            directorService.updateAttendanceTime(examNo, examineeNo);
            resultMap.put("message", "출석시간이 업데이트 되었습니다.");
            resultMap.put("code", HttpStatus.OK.value());
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }


}
