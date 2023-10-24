package multicampussa.laams.director.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamExamineeListDto;
import multicampussa.laams.director.dto.ExamInformationDto;
import multicampussa.laams.director.dto.ExamMonthDayListDto;
import multicampussa.laams.director.service.DirectorService;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/director")
public class DirectorController {

    private final DirectorService directorService;

    // 시험 목록 조회
//    @GetMapping("/{directorNo}/exams")
//    public List<ExamListDto> getExams(@PathVariable Long directorNo){
//        return directorService.getExamList(directorNo);
//    }

    // 시험 월별 및 일별 조회
    @GetMapping("/{directorNo}/exams")
    public List<ExamMonthDayListDto> getExamMonthDayList(@PathVariable Long directorNo, @RequestParam int year, @RequestParam int month, @RequestParam(value = "day", defaultValue = "0") int day){
        return directorService.getExamMonthDayList(directorNo, year, month, day);
    }

    // 시험 상세정보 조회
    @GetMapping("/exams/{examNo}")
    public ResponseEntity<?> getExamInformation(@PathVariable Long examNo){
        ExamInformationDto examInformationDto = directorService.getExamInformation(examNo);
        return ResponseEntity.ok(examInformationDto);
    }

    // 시험 응시자 목록 조회
    @GetMapping("/exams/{examNo}/examinees")
    public List<ExamExamineeListDto> getExamExamineeList(@PathVariable Long examNo){
        return directorService.getExamExamineeList(examNo);
    }

}
