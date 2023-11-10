package multicampussa.laams.manager.domain.examinee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamExamineeRepository extends JpaRepository<ExamExaminee, Long> {

    // 페이징 처리
    @Query("SELECT ee FROM ExamExaminee ee WHERE ee.compensationStatus = :status")
    Page<ExamExaminee> findAllByStatus(@Param("status") ExamExaminee.CompensationValue status, Pageable pageable);

    // 시험_응시자 엔티티에서 시험 no에 해당하는 응시자 전체 조회
    List<ExamExaminee> findByExamNo(Long examNo);

    // 출석이 true이고 해당 시험 번호의 응시자 전체 조회
    List<ExamExaminee> findByExamNoAndAttendance(Long examNo, Boolean attendance);

    // 보상대상 여부가 true이고 해당 시험 번호의 응시자 전체 조회
    List<ExamExaminee> findByExamNoAndCompensation(Long examNo, Boolean compensation);

    // 보상 대상자가 true인 응시자 전체 조회
    List<ExamExaminee> findByCompensation(Boolean compensation);

    // 시험 응시자 상세 조회
    ExamExaminee findByExamNoAndExamineeNo(Long examNo, Long examineeNo);

    // 응시자 번호로 조회
    ExamExaminee findByExamineeNo(Long examineeNo);

    // 시험 응시자 수 카운트
    @Query(value = "SELECT count(ee) FROM ExamExaminee ee WHERE ee.exam.no = :examNo")
    int countByExamineeNo(Long examNo);

    // 시험 출결 여부 카운트(지각 안하고 제시간에 온 응시자들)
    @Query(value = "SELECT count(ee) FROM ExamExaminee ee WHERE ee.exam.no = :examNo AND ee.attendance = true And ee.attendanceTime < ee.exam.examDate")
    int countByAttendance(Long examNo);

    // 시험 서류 지참 카운트
    @Query(value = "SELECT count(ee) FROM ExamExaminee ee WHERE ee.exam.no = :examNo AND ee.document = '서류_제출_완료'")
    int countByDocument(Long examNo);

    // 생성날짜와 보상여부로 조회
    @Query("SELECT ee FROM ExamExaminee ee WHERE DATE(ee.exam.examDate) = :targetDate AND ee.compensation = true AND ee.compensationStatus = '보상_대기'")
    List<ExamExaminee> findUncompensatedByDate(@Param("targetDate") java.sql.Date targetDate);
}

