package multicampussa.laams.director.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.director.domain.errorReport.ErrorReport;
import multicampussa.laams.director.dto.director.*;
import multicampussa.laams.director.dto.errorReport.ErrorReportCreateDto;
import multicampussa.laams.director.repository.DirectorRepository;
import multicampussa.laams.director.repository.errorReport.ErrorReportRepository;
import multicampussa.laams.global.ApiResponse;
import multicampussa.laams.global.CustomExceptions;
import multicampussa.laams.manager.domain.exam.Exam;
import multicampussa.laams.manager.domain.exam.ExamDirector;
import multicampussa.laams.manager.domain.exam.ExamDirectorRepository;
import multicampussa.laams.manager.domain.exam.ExamRepository;
import multicampussa.laams.manager.domain.examinee.ExamExaminee;
import multicampussa.laams.manager.domain.examinee.ExamExamineeRepository;
import org.joda.time.LocalTime;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import multicampussa.laams.director.service.LocationDistance;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import static org.joda.time.LocalTime.*;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final ExamRepository examRepository;
    private final ExamExamineeRepository examExamineeRepository;
    private final ExamDirectorRepository examDirectorRepository;
    private final ErrorReportRepository errorReportRepository;


    // 파일 헤더 설정을 위한 메서드
    private HttpHeaders getMultipartHeaders(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getContentType()));
        headers.setContentDispositionFormData(file.getName(), file.getOriginalFilename());
        return headers;
    }

    // 얼굴 일치 비교
    public String compareFace(
            MultipartFile existingPhoto,
            MultipartFile newPhoto,
            String applicantName,
            String applicantNo ) {

        try {
            // 스프링부트에서 장고 API 호출
            String djangoApiUrl = "[서버주소]/opencv/api/v1/comparison";
//            String djangoApiUrl = "http://localhost:8000/api/v1/comparison";

            // 필요한 헤더 설정 등 필요에 따라 커스터마이징
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 파일 및 기타 데이터를 MultiValueMap에 넣기
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("existingPhoto", new HttpEntity<>(existingPhoto.getBytes(), getMultipartHeaders(existingPhoto)));
            body.add("newPhoto", new HttpEntity<>(newPhoto.getBytes(), getMultipartHeaders(newPhoto)));
            body.add("applicantName", applicantName);
            body.add("applicantNo", applicantNo);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

            // RestTemplate을 사용하여 장고 API 호출
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    djangoApiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            String data = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();


            JsonNode jsonNode = objectMapper.readTree(data);

            // 데이터 파싱 부분
            double matchRate = jsonNode.get(0).asDouble();
            int percentage = (int) (matchRate);

            double normalCriteria = 0.6;
            boolean normalPass = jsonNode.get(1).asDouble() < normalCriteria;
            String normalResult = normalPass ? "통과" : "미통과";

            double strictCriteria = 0.5;
            boolean strictPass = jsonNode.get(1).asDouble() < strictCriteria;
            String strictResult = strictPass ? "통과" : "미통과";

            // jsonData에 넣을 문자열 생성
            String jsonData = String.format("일치율: %d%%, 정상적 기준: %s, 엄격한 기준: %s", percentage, normalResult, strictResult);

            return jsonData;

        } catch (Exception e) {
            throw new CustomExceptions.FaceCompareException("이미지와 요청 쿼리를 확인하세요.");
        }
    }

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
    public ExamStatusDto getExamStatus(Long examNo, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){
            Exam exam = examRepository.findById(examNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 시험은 없습니다."));

            int examineeCnt = examExamineeRepository.countByExamineeNo(examNo);
            int attendanceCnt = examExamineeRepository.countByAttendance(examNo);
            int documentCnt = examExamineeRepository.countByDocument(examNo);

            // 감독관 자신의 시험 출석 조회 여부(했으면 1, 안했으면 0)
            boolean directorAttendance = examDirectorRepository.findByDirectorIdAndExamNo(directorId, examNo);

            return new ExamStatusDto(examineeCnt, attendanceCnt, documentCnt, directorAttendance);
        }
        else{
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

    }

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
    public void requestExamAssignment(Long examNo, String authority, String directorId, Long centerNo) {
        System.out.println(directorId);
        if(authority.equals("ROLE_DIRECTOR")){
            // 자기 센터 시험만 신청 가능하니까
            List<Exam> exams = examRepository.findByCenterNo(centerNo);
            Director director = directorRepository.findById(directorId);
            if(exams == null) {
                throw new IllegalArgumentException("현재 센터에 시험이 없습니다.");
            }else {
                // 시험 있으면 배정 요청
                boolean examFound = false;
                for (Exam exam : exams){
                    if(exam.getNo() == examNo){
                        int assignedExamDirector = examDirectorRepository.findByExamNoAndDirectorId(examNo, directorId);
                        if(assignedExamDirector == 0){
                            ExamDirector examDirector = new ExamDirector();
                            examDirector.setExam(exam, director);
                            examDirectorRepository.save(examDirector);
                            examFound = true;
                            break;
                        } else {
                            throw new IllegalArgumentException("이미 배정 요청한 시험입니다.");
                        }
                    }else{
                        continue;
                    }
                }
                if(!examFound){
                    throw new IllegalArgumentException("현재 센터에는 이 시험이 없습니다.");
                }
            }
        } else{
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
                        boolean compensation = true;
                        examExaminee.setCompensation(compensationApplyDto, compensation);
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

                        LocalDateTime now = LocalDateTime.now();
                        Duration duration = Duration.between(now, exam.getExamDate());
                        if(duration.toHours() >= 1) {
                            throw new IllegalArgumentException("시험 시작 시간 한시간 전부터 출석 인증이 가능합니다");
                        } else {
                            if(directorAttendanceRequestDto.getLatitude() != null && directorAttendanceRequestDto.getLongitude() != null) {
                                Double directorLatitude = directorAttendanceRequestDto.getLatitude();
                                Double directorLongitude = directorAttendanceRequestDto.getLongitude();

                                Double centerLatitude = exam.getCenter().getLatitude();
                                Double centerLongitude = exam.getCenter().getLongitude();

                                // 거리 계산
                                Double dist = LocationDistance.distance(directorLatitude, directorLongitude ,centerLatitude, centerLongitude, "meter");
                                if(dist > 500) { // 500m 초과이면
                                    throw new IllegalArgumentException("센터 거리 500m내에서 인증해 주세요.");
                                }
                                else {
                                    Boolean directorAttendance = true;
                                    DirectorAttendanceDto directorAttendanceDto = new DirectorAttendanceDto(directorAttendance);

                                    if(currentExamDirector.getDirectorAttendance() != true) {
                                        currentExamDirector.updateAttendance(directorAttendanceDto);
                                    } else {
                                        throw new IllegalArgumentException(exam.getNo() + "번 시험은 이미 도착 인증을 했습니다.");
                                    }
                                    return directorAttendanceDto;
                                }
                            } else {
                                throw new IllegalArgumentException("위치를 인식할 수 없습니다.");
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
    }


    // 홈화면에서 감독관 센터 도착
    @Transactional
    public DirectorAttendanceDto attendanceDirectorHome(DirectorAttendanceRequestDto directorAttendacneRequestDto, String authority, String directorId) {
        if(authority.equals("ROLE_DIRECTOR")){

            LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), java.time.LocalTime.MIDNIGHT);
            LocalDateTime endOfToday = LocalDateTime.of(LocalDate.now().plusDays(1), java.time.LocalTime.MIDNIGHT.minusNanos(1));
            // 감독관이 감독하는 시험들 중에서 오늘 시험 리스트 가져오기
            List<Exam> exams = examRepository.findByDirectorIdToday(directorId, startOfToday, endOfToday);
            if(exams.isEmpty()) {
                throw new IllegalArgumentException("감독관이 감독할 오늘 시험은 없습니다.");
            } else {

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime closestExamTime = null;
                Exam closestExam = null;

                for(Exam exam : exams){
                    LocalDateTime examDate = exam.getExamDate();
                    // 오늘 시험 중 현재 시간 보다 늦고 그 중 가장 빠른 시험 찾기
                    if(examDate.isAfter(now) && (closestExam == null || examDate.isBefore(closestExamTime))) {
                        closestExam = exam;
                        closestExamTime = examDate;
                    }
                }

                Duration duration = Duration.between(now, closestExamTime);
                if(duration.toHours() >= 1) {
                    throw new IllegalArgumentException("시험 시작 시간 한시간 전부터 출석 인증이 가능합니다");
                } else {

                    if(directorAttendacneRequestDto.getLatitude() != null && directorAttendacneRequestDto.getLongitude() != null) {
                        Double directorLatitude = directorAttendacneRequestDto.getLatitude();
                        Double directorLongitude = directorAttendacneRequestDto.getLongitude();

                        Double centerLatitude = closestExam.getCenter().getLatitude();
                        Double centerLongitude = closestExam.getCenter().getLongitude();

                        // 거리 계산
                        Double dist = LocationDistance.distance(directorLatitude, directorLongitude ,centerLatitude, centerLongitude, "meter");
                        if(dist > 500) { // 500m 초과이면
                            throw new IllegalArgumentException("센터 거리 500m내에서 인증해 주세요.");
                        }
                        else {
                            System.out.println(closestExam.getNo());
                            Boolean directorAttendance = true;
                            DirectorAttendanceDto directorAttendanceDto = new DirectorAttendanceDto(directorAttendance);

                            ExamDirector currentExamDirector = examDirectorRepository.findByDirectorAndExam(directorId, closestExam.getNo());
                            if(currentExamDirector.getDirectorAttendance() != true) {
                                currentExamDirector.updateAttendance(directorAttendanceDto);
                            } else {
                                throw new IllegalArgumentException(closestExam.getNo() + "번 시험은 이미 도착 인증을 했습니다.");
                            }

                            return directorAttendanceDto;
                        }
                    } else {
                        throw new IllegalArgumentException("위치를 인식할 수 없습니다.");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 내가 속한 센터에 내가 아직 신청 요청 안 보내고, 꽉 차지 않는 시험 +
    // 내가 신청했지만 승인 안된 시험 목록
    @Transactional
    public List<UnappliedAndUnapprovedExamListDto> unappliedAndUnapprovedExamList(String authority, String directorId, Long centerNo, int year, int month, int day) {
        if (authority.equals("ROLE_DIRECTOR")) {

            List<UnappliedAndUnapprovedExamListDto> unappliedAndUnapprovedExamListDtos = new ArrayList<>();
            // 내가 속한 센터이 시험들
            List<Exam> centerExams = examRepository.findByCenterNoContainingDate(centerNo, year, month, day);

            for(Exam exam : centerExams){
                int cntConfirmDirector = examDirectorRepository.countByConfirm(exam.getNo());
                // 감독관이 시험 배정 요청 했는지 (했으면 0, 안했으면 1)
                boolean unapplied = examDirectorRepository.findByDirectorIdAndExam(exam.getNo(), directorId);
                if(unapplied) {
                    // 현재 시험에서 배정 요청이 승인된 감독관 수
                    int confirmedDirectorCnt = examDirectorRepository.countByConfirm(exam.getNo());

                    // 최대 배정 가능한 감독관 수와 비교해서 배정이 가능하면(꽉 차지 않았으면)
                    if(exam.getMaxDirector() > confirmedDirectorCnt) {
                        unappliedAndUnapprovedExamListDtos.add(new UnappliedAndUnapprovedExamListDto(exam, "미배치", cntConfirmDirector));
                    }
                } else {
                    // 배정 요청했을 때의 현재 시험 감독관 찾기
                    ExamDirector currentExamDirector = examDirectorRepository.findByDirectorAndExam(directorId, exam.getNo());

                    // 승인이 안됨
                    if(currentExamDirector.getConfirm().toString().equals("대기") || currentExamDirector.getConfirm().toString().equals("거절")) {
                        unappliedAndUnapprovedExamListDtos.add(new UnappliedAndUnapprovedExamListDto(exam, currentExamDirector.getConfirm().toString(), cntConfirmDirector));
                    }
                }
            }
            return unappliedAndUnapprovedExamListDtos;

        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }

    // 내가 속한 센터에서 신청이 가능한 시험 목록
    @Transactional
    public List<PossibleToApplyExamListDto> possibleToApplyExamList(String authority, String directorId, Long centerNo, int year, int month, int day) {
        if (authority.equals("ROLE_DIRECTOR")) {

            List<PossibleToApplyExamListDto> possibleToApplyExamListDtos = new ArrayList<>();
            // 내가 속한 센터의 시험들
            List<Exam> centerExams = examRepository.findByCenterNoContainingDate(centerNo, year, month, day);

            for(Exam exam : centerExams){
                int cntConfirmDirector = examDirectorRepository.countByConfirm(exam.getNo());
                // 감독관이 시험 배정 요청 했는지 (했으면 0, 안했으면 1)
                boolean unapplied = examDirectorRepository.findByDirectorIdAndExam(exam.getNo(), directorId);
                if(unapplied) {
                    // 현재 시험에서 배정 요청이 승인된 감독관 수
                    int confirmedDirectorCnt = examDirectorRepository.countByConfirm(exam.getNo());

                    // 최대 배정 가능한 감독관 수와 비교해서 배정이 가능하면(꽉 차지 않았으면)
                    if(exam.getMaxDirector() > confirmedDirectorCnt) {
                        possibleToApplyExamListDtos.add(new PossibleToApplyExamListDto(exam, cntConfirmDirector));
                    }
                }
            }
            return possibleToApplyExamListDtos;

        } else {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }
    }
}
