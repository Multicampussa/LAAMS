package multicampussa.laams.director.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.*;
import multicampussa.laams.director.service.DirectorService;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/director")
@Api(tags = "감독관")
public class DirectorController {

    private final DirectorService directorService;

    // 시험 목록 조회
//    @GetMapping("/{directorNo}/exams")
//    public List<ExamListDto> getExams(@PathVariable Long directorNo){
//        return directorService.getExamList(directorNo);
//    }

    @ApiOperation(value = "시험 월별 조회 및 일별 조회")
    @GetMapping("/{directorNo}/exams")
    public List<ExamMonthDayListDto> getExamMonthDayList(@PathVariable Long directorNo, @RequestParam int year, @RequestParam int month, @RequestParam(value = "day", defaultValue = "0") int day){
        return directorService.getExamMonthDayList(directorNo, year, month, day);
    }

    @ApiOperation(value = "시험 상세정보 조회")
    @GetMapping("/exams/{examNo}")
    public ResponseEntity<?> getExamInformation(@PathVariable Long examNo){
        ExamInformationDto examInformationDto = directorService.getExamInformation(examNo);
        return ResponseEntity.ok(examInformationDto);
    }

    @ApiOperation(value = "시험 응시자 목록 조회")
    @GetMapping("/exams/{examNo}/examinees")
    public List<ExamExamineeListDto> getExamExamineeList(@PathVariable Long examNo){
        return directorService.getExamExamineeList(examNo);
    }

    @ApiOperation(value = "시험 응시자 상세 조회")
    @GetMapping("/exams/{examNo}/examinees/{examineeNo}")
    public ResponseEntity<?> getExamExaminee(@PathVariable Long examNo, @PathVariable Long examineeNo){
        ExamExamineeDto examExamineeDto = directorService.getExamExaminee(examNo, examineeNo);
        return ResponseEntity.ok(examExamineeDto);
    }

    @ApiOperation(value = "시험 응시자 현황 조회")
    @GetMapping("/exams/{examNo}/status")
    public ResponseEntity<?> getExamStatus(@PathVariable Long examNo){
        ExamStatusDto examStatusDto = directorService.getExamStatus(examNo);
        return ResponseEntity.ok(examStatusDto);
    }

}
