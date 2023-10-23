package multicampussa.laams.director.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamMonthListDto;
import multicampussa.laams.director.service.DirectorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // 시험 월별 조회
    @GetMapping("/{directorNo}/exams/{year}/{month}")
    public List<ExamMonthListDto> getExamMonthList(@PathVariable Long directorNo, @PathVariable Integer year, @PathVariable Integer month){
        return directorService.getExamMonthList(directorNo, year, month);
    }
}
