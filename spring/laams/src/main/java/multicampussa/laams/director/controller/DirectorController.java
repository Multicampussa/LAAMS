package multicampussa.laams.director.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamMonthDayListDto;
import multicampussa.laams.director.service.DirectorService;
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
}