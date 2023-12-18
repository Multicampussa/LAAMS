package multicampussa.laams.manager.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamDirectorRepository extends JpaRepository<ExamDirector, Long> {

    // 시험 정보로 ExamDirector 조회
    List<ExamDirector> findByExam(Exam exam);

    // 시험 no로 ExamDirector 조회
    List<ExamDirector> findByExamNo(Long examNo);

    // 시험 no로 confrim이 대기인 것들 조회
    @Query("SELECT ed FROM ExamDirector ed WHERE ed.exam.no = :examNo AND ed.confirm = '대기'")
    List<ExamDirector> findUnconfirmed(@Param("examNo") Long examNo);

    // 시험 no과 감독 no로 조회
    ExamDirector findByExamNoAndMemberNo(Long examNo, Long memberNo);

    // ExamDirector와 연결된 시험의 날짜와 confirm이 false인 것들 조회
    @Query("SELECT ed FROM ExamDirector ed WHERE DATE(ed.exam.examDate) = :targetDate AND ed.confirm = '대기'")
    List<ExamDirector> findUnconfirmedByDate(@Param("targetDate") java.sql.Date targetDate);

    // 시험 번호와 confirm이 false인 ExamDirector 조회
    List<ExamDirector> findByExamNoAndConfirm(Long examNo, Boolean confirm);

    @Query("select count(*) from ExamDirector ed where ed.exam.no = :no and ed.confirm = '승인'")
    int countByConfirm(Long no);

    @Query("select count(*) from ExamDirector ed where ed.exam.no = :examNo and ed.member.id = :directorId")
    int findByExamNoAndDirectorId(Long examNo, String directorId);

    // 현재 이 시험에 이 감독관이 시험 배정 요청을 했는지 (했으면 0 안했으면 1)
    @Query("select case when count(*) > 0 then false else true end from ExamDirector ed where ed.exam.no = :examNo and ed.member.id = :directorId")
    boolean findByDirectorIdAndExam(Long examNo, String directorId);

    // 배정 요청했을 때의 시험 감독관 찾기
    @Query("select ed from ExamDirector ed where ed.member.id = :directorId and ed.exam.no = :examNo")
    ExamDirector findByDirectorAndExam(String directorId, Long examNo);

    // 감독관의 시험 출석 여부 조회 (출석했으면 1 안했으면0)
    @Query("select case when count(*) > 0 then true else false end from ExamDirector ed where ed.member.id = :directorId and ed.exam.no = :examNo and ed.directorAttendance = true")
    boolean findByDirectorIdAndExamNo(String directorId, Long examNo);
}
