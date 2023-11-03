package multicampussa.laams.director.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.director.dto.director.*;
import multicampussa.laams.director.dto.errorReport.ErrorReportCreateDto;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.director.repository.errorReport.ErrorReportRepository;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import multicampussa.laams.director.service.LocationDistance;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final ExamRepository examRepository;
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamDirectorRepository examDirectorRepository;
    private final ErrorReportRepository errorReportRepository;

    // 감독관 시험 월별, 일별 조회
    @Transactional
    public List<ExamMonthDayListDto> getExamMonthDayList(Long directorNo, int year, int month, int day, String authority) {

        if(authority.equals("ROLE_DIRECTOR")){
            List<ExamMonthDayListDto> examMonthDayListDtos = new ArrayList<>();
            List<Exam> exams = directorRepository.findAllByDirectorNoContainingMonthAndDay(directorNo, year, month, day);
            for(Exam exam : exams){
                examMonthDayListDtos.add(new ExamMonthDayListDto(exam));
            }
            return examMonthDayListDtos;
        }
        else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 상세정보 조회
    @Transactional
    public ExamInformationDto getExamInformation(Long examNo, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if (exam.isPresent()) {
                List<ExamDirector> examDirectors = exam.get().getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                if(isDirectorExists){
                    return new ExamInformationDto(exam.get());
                }else {
                    throw new IllegalArgumentException("감독 권한이 없는 시험입니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 응시자 목록 조회
    @Transactional
    public List<ExamExamineeListDto> getExamExamineeList(Long examNo, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                List<ExamDirector> examDirectors = exam.get().getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                if(isDirectorExists){
                    List<ExamExamineeListDto> examExamineeListDtos = new ArrayList<>();
                    List<ExamExaminee> examExaminees = examExamineeRepository.findByExamNo(examNo);
                    for(ExamExaminee examExaminee : examExaminees){
                        examExamineeListDtos.add(new ExamExamineeListDto(examExaminee));
                    }
                    return examExamineeListDtos;
                } else {
                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
                }
            }else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 응시자 상세 조회
    @Transactional
    public ExamExamineeDto getExamExaminee(Long examNo, Long examineeNo, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                List<ExamDirector> examDirectors = exam.get().getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                if(isDirectorExists){
                    Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
                    if(examExaminee.isEmpty()){
                        throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
                    }else {
                        return new ExamExamineeDto(examExaminee.get());
                    }
                } else {
                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 시험 현황 조회
    @Transactional
    public ExamStatusDto getExamStatus(Long examNo, String authority) {
        if(authority.equals("ROLE_DIRECTOR")){
            Exam exam = examRepository.findById(examNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 시험은 없습니다."));

            int examineeCnt = examExamineeRepository.countByExamineeNo(examNo);
            int attendanceCnt = examExamineeRepository.countByAttendance(examNo);
//        int documentCnt = examExamineeRepository.countByDocument(examNo);

            return new ExamStatusDto(examineeCnt, attendanceCnt);
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

    }

    // 응시자 출석 시간 업데이트 (응시자 지각여부 판단)
//    @ Transactional
//    public UpdateAttendanceDto updateAttendanceTime(Long examNo, Long examineeNo) {
//
//        Optional<Exam> exam = examRepository.findById(examNo);
//        System.out.println(examNo);
//        if(exam.isPresent()){
//            Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
//            if(examExaminee.isEmpty()){
//                throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
//            }else {
//                // 출석 시간 업데이트
//                examExaminee.get().updateAttendanceTime(LocalDateTime.now());
//                examExamineeRepository.save(examExaminee.get());
//
//                return new UpdateAttendanceDto(examineeNo, LocalDateTime.now());
//            }
//        } else {
//            throw new IllegalArgumentException("해당 시험은 없습니다.");
//        }
//    }

    // 응시자 출석 확인
//    @Transactional
//    public CheckAttendanceDto checkAttendance(Long examNo, Long examineeNo, String authority, String directorId) {
//        if(authority.equals("ROLE_DIRECTOR")){
//            Optional<Exam> exam = examRepository.findById(examNo);
//            if(exam.isPresent()){
//                List<ExamDirector> examDirectors = exam.get().getExamDirector();
//                boolean isDirectorExists = examDirectors.stream()
//                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
//                if(isDirectorExists){
//                    Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
//                    if(examExaminee.isEmpty()){
//                        throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
//                    }else {
//                        // 응시자의 출석 시간과 시험 시작 시간 비교
//                        LocalDateTime examineeAttendanceTime =examExaminee.get().getAttendanceTime();
//                        LocalDateTime examStartTime = exam.get().getExamDate();
//                        // 응시자의 출석 시간이 더 빠름(지각x)
//                        if(examineeAttendanceTime.isBefore(examStartTime)){
//                            Boolean attendance = true;
//                            Boolean compensation = false;
//                            String compensationType = null;
//
//                            CheckAttendanceDto checkAttendanceDto = new CheckAttendanceDto(attendance, compensation, compensationType);
//                            examExaminee.get().updateAttendace(checkAttendanceDto);
//                            return checkAttendanceDto;
//                        }else{
//                            Boolean attendance = true;
//                            Boolean compensation = true;
//                            String compensationType = "지각";
//
//                            CheckAttendanceDto checkAttendanceDto = new CheckAttendanceDto(attendance, compensation, compensationType);
//                            examExaminee.get().updateAttendace(checkAttendanceDto);
//                            return checkAttendanceDto;
//                        }
//                    }
//                } else {
//                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
//                }
//            } else {
//                throw new IllegalArgumentException("해당 시험은 없습니다.");
//            }
//        }
//        else{
//            throw new IllegalArgumentException("접근 권한이 없습니다.");
//        }
//
//    }

    // 출석 확인
    @Transactional
    public CheckAttendanceDto checkAttendance(Long examNo, Long examineeNo, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                List<ExamDirector> examDirectors = exam.get().getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                if(isDirectorExists){
                    Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
                    if(examExaminee.isEmpty()){
                        throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
                    }else {

                        examExaminee.get().updateAttendanceTime(LocalDateTime.now());
                        examExamineeRepository.save(examExaminee.get());

                        // 응시자의 출석 시간과 시험 시작 시간 비교
                        LocalDateTime examineeAttendanceTime =examExaminee.get().getAttendanceTime();
                        LocalDateTime examStartTime = exam.get().getExamDate();
                        // 응시자의 출석 시간이 더 빠름(지각x)
                        if(examineeAttendanceTime.isBefore(examStartTime)){
                            Boolean attendance = true;
                            Boolean compensation = false;
                            String compensationType = null;

                            CheckAttendanceDto checkAttendanceDto = new CheckAttendanceDto(LocalDateTime.now(), attendance, compensation, compensationType);
                            examExaminee.get().updateAttendace(checkAttendanceDto);
                            return checkAttendanceDto;
                        }else{
                            Boolean attendance = true;
                            Boolean compensation = true;
                            String compensationType = "지각";

                            CheckAttendanceDto checkAttendanceDto = new CheckAttendanceDto(LocalDateTime.now(), attendance, compensation, compensationType);
                            examExaminee.get().updateAttendace(checkAttendanceDto);
                            return checkAttendanceDto;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

    }

    // 서류 제출 확인
    @Transactional
    public CheckDocumentDto checkDocument(Long examNo, Long examineeNo, DocumentRequestDto documentRequestDto, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Optional<Exam> exam = examRepository.findById(examNo);
            if(exam.isPresent()){
                List<ExamDirector> examDirectors = exam.get().getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                if(isDirectorExists){
                    Optional<ExamExaminee> examExaminee = Optional.ofNullable(examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo));
                    if(examExaminee.isEmpty()){
                        throw new IllegalArgumentException("해당 시험의 응시자가 아닙니다.");
                    }else {
                        // 출석이 true인 응시자 중에서 서류 제출 여부 확인하기
                        if(examExaminee.get().getAttendance() == true){
                            // 서류 있음
                            System.out.println(documentRequestDto.getDocument());
                            if(documentRequestDto.getDocument().toString().equals("서류_제출_완료")){
                                String document = documentRequestDto.getDocument().toString();
                                Boolean compensation = false;
                                String compensationType = "";

                                CheckDocumentDto checkDocumentDto = new CheckDocumentDto(document, compensation, compensationType);
                                examExaminee.get().updateDocument(checkDocumentDto);
                                return checkDocumentDto;
                            }else{
                                // 서류가 없으면 서류 미제출, 보상여부 true, 보상타입 서류 미제출로
                                String document = documentRequestDto.getDocument().toString();
                                Boolean compensation = true;
                                String compensationType = "서류 미제출";

                                CheckDocumentDto checkDocumentDto = new CheckDocumentDto(document, compensation, compensationType);
                                examExaminee.get().updateDocument(checkDocumentDto);
                                return checkDocumentDto;
                            }

                        }else {
                            throw new IllegalArgumentException("출석하지 않은 응시자 입니다.");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
                }
            } else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

    }


    // 감독관 시험 배정 요청
    // 요청 한 번 했으면 더 안되게 만들어야함
    @Transactional
    public void requestExamAssignment(Long examNo, String authority, String directorId) {
        System.out.println(directorId);
        if(authority.equals("ROLE_DIRECTOR")){
            Exam exam = examRepository.findById(examNo).orElse(null);
            Director director = directorRepository.findById(directorId);
            // 시험 있으면 배정 요청(
            if(exam != null){
                ExamDirector examDirector = new ExamDirector();
                examDirector.setExam(exam, director);
                examDirectorRepository.save(examDirector);
            }
            else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }

        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }


    // 보상 신청
    @Transactional
    public void applyCompensation(Long examNo, Long examineeNo, CompensationApplyDto compensationApplyDto, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Exam exam = examRepository.findById(examNo).orElse(null);
            if(exam != null){
                List<ExamDirector> examDirectors = exam.getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                if(isDirectorExists){
                    ExamExaminee examExaminee = examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo);
                    if(examExaminee != null){
                        if(compensationApplyDto.getCompensationType().isEmpty()){
                            throw new IllegalArgumentException("보상타입이 없습니다.");
                        }

                        examExaminee.setCompensation(compensationApplyDto);
                        examExamineeRepository.save(examExaminee);
                    }
                    else {
                        throw new IllegalArgumentException("해당 시험의 응시자가 없습니다.");
                    }
                } else {
                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
                }
            }
            else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 에러리포트 작성
    @Transactional
    public void createErrorReport(ErrorReportCreateDto errorReportCreateDto, String authority, Long directorNo) {
        if(authority.equals("ROLE_DIRECTOR")) {
            ErrorReport errorReport = new ErrorReport();
            if(directorRepository.existsById(directorNo)){
                Director director = directorRepository.findById(directorNo).get();

                errorReport.toEntity(errorReportCreateDto, director);
                errorReportRepository.save(errorReport);
            }else {
                throw new IllegalArgumentException(directorNo + "가 없습니다.");
            }
        }else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 감독관 센터 도착
    @Transactional
    public DirectorAttendanceDto attendanceDirector(Long examNo, Long directorNo, DirectorAttendanceRequestDto directorAttendanceRequestDto, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")) {
            Exam exam = examRepository.findById(examNo).orElse(null);
            if(exam != null){
                List<ExamDirector> examDirectors = exam.getExamDirector();
                boolean isDirectorExists = examDirectors.stream()
                        .anyMatch(examDirector -> examDirector.getDirector().getId().equals(directorId));
                // 자신이 감독하는 시험인지
                if(isDirectorExists){
                    ExamDirector currentExamDirector = examDirectors.stream()
                            .filter(examDirector -> examDirector.getDirector().getNo() == directorNo)
                            .findFirst()
                            .orElse(null);

                    System.out.println(currentExamDirector.getNo()); // 아직 directorNo는 사용 안하는 중..
                    // 현재 이 시험을 감독하는 사람인지
                    if(currentExamDirector != null){

                        Double directorLatitude = directorAttendanceRequestDto.getLatitude();
                        Double directorLongitude = directorAttendanceRequestDto.getLongitude();
                        if(directorLatitude != null && directorLongitude != null) {

                            Double centerLatitude = exam.getCenter().getLatitude();
                            Double centerLongitude = exam.getCenter().getLongitude();

                            // 거리 계산
                            Double dist = LocationDistance.distance(directorLatitude, directorLongitude ,centerLatitude, centerLongitude, "meter");
                            if(dist > 500) { // 500m 초과이면
                                throw new IllegalArgumentException("아직 멀어요...");
                            }
                            else {
                                Boolean directorAttendance = true;
                                DirectorAttendanceDto directorAttendanceDto = new DirectorAttendanceDto(directorAttendance);

                                currentExamDirector.updateAttendance(directorAttendanceDto);
                                return directorAttendanceDto;
                            }
                        }
                    } else {
                        throw new IllegalArgumentException("현재 시험을 감독하는 사람이 아닙니다.");
                    }
                } else {
                    throw new IllegalArgumentException("감독 권한이 없는 사람입니다.");
                }
            }else {
                throw new IllegalArgumentException("해당 시험은 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
        return null;
    }


//    @Transactional
//    public DirectorAttendanceDto attendanceDirectorHome(Long directorNo, DirectorAttendanceRequestDto directorAttendacneRequestDto, String authority, String directorId) {
//        if(authority.equals("ROLE_DIRECTOR")){
//            List<Exam> exams = examRepository.findByDirectorId(directorId);
//            System.out.println(exams);
//            System.out.println(directorNo); // 아직 안 씀;
//            // 시험들 중에서 시험 일자가 오늘인 것, 그중 한시간 이내인 것 찾아서 들고옴
//            // 그 시험의 센터장 위치랑 감독관 현재 위치랑 비교
//            // 출석 인증 ?
//            for(Exam exam : exams){
//                if(exam.getExamDate().)
//            }
//
//        } else {
//            throw new IllegalArgumentException("접근 권한이 없습니다.");
//        }
//    }

}
