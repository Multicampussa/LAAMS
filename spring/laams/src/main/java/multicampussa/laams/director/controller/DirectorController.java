package multicampussa.laams.director.controller;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.dto.ExamListDto;
import multicampussa.laams.director.service.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/director")

public class DirectorController {

    private final DirectorService directorService;

    // 시험 목록 조회
    @GetMapping("/{directorNo}/exams")
    public List<ExamListDto> getExams(@PathVariable Long directorNo){
        return directorService.getExamList(directorNo);
    }

}
