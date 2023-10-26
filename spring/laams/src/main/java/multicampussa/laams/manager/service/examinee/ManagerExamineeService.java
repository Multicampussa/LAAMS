package multicampussa.laams.manager.service.examinee;

import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import multicampussa.laams.manager.domain.examinee.Examinee;
import multicampussa.laams.manager.domain.examinee.ExamineeRepository;
import multicampussa.laams.manager.dto.examinee.request.ExamineeCreateRequest;
import multicampussa.laams.manager.dto.examinee.request.ExamineeUpdateRequest;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationDetailResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeCompensationListResponse;
import multicampussa.laams.manager.dto.examinee.response.ExamineeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerExamineeService {

    private final ExamineeRepository examineeRepository;
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamDirectorRepository examDirectorRepository;

    public ManagerExamineeService(ExamineeRepository examineeRepository, ExamExamineeRepository examExamineeRepository, ExamDirectorRepository examDirectorRepository) {
        this.examineeRepository = examineeRepository;
        this.examExamineeRepository = examExamineeRepository;
        this.examDirectorRepository = examDirectorRepository;
    }

    // 응시자 생성
    @Transactional
    public void saveExaminee(ExamineeCreateRequest request) {
        Examinee examinee = examineeRepository.save(new Examinee(request.getName(), request.getAge(),
                request.getPhoneNum(), request.getGender(), request.getId(), request.getPw()));
    }

    // 응시자 목록 조회
    @Transactional(readOnly = true)  // 데이터의 변경 없고 조회만 기능
    public List<ExamineeResponse> getExaminees() {
        return examineeRepository.findAll().stream()  // 모든 Examinee 엔티티 검색하고 Stream<Examinee> 형태로 반환
                .map(ExamineeResponse::new)  // mpa 메서드 사용하여 각 스트림 요소에 ExamineeResponse 생성자 호출 및 Examinee 엔티티를 ExamineeResponse로 변환
                .collect(Collectors.toList());  // map 메서드로 생성된 ExamineeResponse 객체를 다시 리스트로 수집

    }

    // 보상대상자 목록 조회
    @Transactional
    public List<ExamineeCompensationListResponse> getCompensationList() {
        // 반환할 응답
        List<ExamineeCompensationListResponse> compensationListResponse = new ArrayList<>();

        // 보상대상자 담긴 시험_응시자 객체 리스트
        List<ExamExaminee> compensationList = examExamineeRepository.findByCompensation(true);

        // 시험_응시자의 시험 no를 이용하여 시험_감독 객체 생성
        for (ExamExaminee examExaminee : compensationList) {
            // 시험_응시자로 보상리스트 생성 및 응답 리스트에 추가
            ExamineeCompensationListResponse examineeCompensationListResponse = new ExamineeCompensationListResponse(examExaminee);
            compensationListResponse.add(examineeCompensationListResponse);
        }

        return compensationListResponse;
    }

    // 보상 대상 응시자 상세 조회
    @Transactional
    public ExamineeCompensationDetailResponse getCompensationDetail(Long examineeNo) {
        ExamExaminee examExaminee = examExamineeRepository.findByExamineeNo(examineeNo);
        ExamineeCompensationDetailResponse examineeCompensationDetailResponse = new ExamineeCompensationDetailResponse(examExaminee);
        return examineeCompensationDetailResponse;
    }


    // 응시자 수정
    @Transactional
    public void updateExaminee(ExamineeUpdateRequest request) {
        Examinee examinee = examineeRepository.findById(request.getNo())
                .orElseThrow(IllegalArgumentException::new);  // id로 검색해서 없으면 오류발생 시킴
        examinee.updateExamineeInfo(request.getName(), request.getAge(), request.getPhoneNum(), request.getGender(), request.getId(), request.getPw());
//        examineeRepository.save(examinee); 영속성 컨텍스트로 생략
    }

    // 응시자 삭제
    @Transactional
    public void deleteExaminee(Long no) {
        Examinee examinee = examineeRepository.findById(no)
                .orElseThrow(IllegalArgumentException::new);
        examineeRepository.delete(examinee);
    }

}
