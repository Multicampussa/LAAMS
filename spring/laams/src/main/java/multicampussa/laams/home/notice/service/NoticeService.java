package multicampussa.laams.home.notice.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.repository.MemberManagerRepository;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.*;
import multicampussa.laams.home.notice.repository.NoticeRepository;
import multicampussa.laams.manager.domain.manager.Manager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NoticeService {

    public final NoticeRepository noticeRepository;
    public final MemberManagerRepository managerRepository;

    public boolean createNotice(NoticeCreateDto noticeCreateDto, Long memberId, String authority) {

        boolean result = true;

        if (noticeCreateDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        } else if (noticeCreateDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }

        if (authority.equals("ROLE_DIRECTOR")) {
            throw new IllegalArgumentException("공지사항 생성 권한이 없습니다.");
        }

        Notice notice = new Notice();
//        Manager manager = new Manager();
//        Manager manager = managerRepository.findById(memberId).get();
        if (managerRepository.existsById(memberId)) {
            Optional<Manager> findMangerById = managerRepository.findById(memberId);

            Manager manager = findMangerById.get();
            notice.toEntity(noticeCreateDto, manager);
            noticeRepository.save(notice);
        } else {
            result = false;
            throw new IllegalArgumentException(memberId + "가 없습니다");
        }
//
//        if (findMangerById.isEmpty()) {
//        } else {
//        }

        return result;
    }

    public boolean updateNotice(NoticeUpdateDto noticeUpdateDto, Long memberId, String authority) {

        boolean result = true;

        if (noticeUpdateDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        } else if (noticeUpdateDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }

        if (authority.equals("ROLE_DIRECTOR")) {
            throw new IllegalArgumentException("공지사항 수정 권한이 없습니다.");
        }

        Long noticeNo = noticeUpdateDto.getNoticeNo();
        Notice notice = noticeRepository.findById(noticeNo).get();
//        Manager manager = new Manager();
//        Manager manager = managerRepository.findById(memberId).get();
        if (managerRepository.existsById(memberId)) {
            notice.update(noticeUpdateDto);
            noticeRepository.save(notice);
        } else {
            result = false;
            throw new IllegalArgumentException(memberId + "가 없습니다");
        }
//
//        if (findMangerById.isEmpty()) {
//        } else {
//        }

        return result;
    }

    public boolean deleteNotice(Long noticeNo, Long memberNo, String authority) {

        boolean result = true;

        Optional<Notice> findNotice = noticeRepository.findById(noticeNo);

        if (findNotice.isEmpty()) {
            // 공지사항이 존재하지 않는 경우
            throw new IllegalArgumentException("존재하지 않는 공지사항입니다.");
        }

        if (authority.equals("ROLE_DIRECTOR")) {
            throw new IllegalArgumentException("공지사항 삭제 권한이 없습니다.");
        }

        noticeRepository.deleteById(noticeNo);

//        if (findNotice.get().getManager().getNo().equals(memberNo)) {
//            noticeRepository.deleteById(noticeNo);
//        } else {
//            throw new IllegalArgumentException("내가 작성한 글이 아닙니다.");
//        }

        return result;
    }

    public List<NoticeListResDto> getNoticeList(int count, int page) {

        int theNumberOfNotice = noticeRepository.getTheNumberOfNotice();
        System.out.println("theNumberOfNotice = " + theNumberOfNotice);

        // Pagination 계산 Class 생성
        Pageable pageable = PageRequest.of(page - 1, count);

        // 반환할 DTO List 생성
        List<NoticeListResDto> res = new ArrayList<>();

        // DB에서 데이터 Pagination 해서 불러오기
        List<Notice> results = noticeRepository.findNoticeWithManagerSortByCreatedAt(pageable);

        // Entity to DTO Mapping
        for (Notice result : results) {

            NoticeListResDto tmp = new NoticeListResDto();

            tmp.toEntity(result);
            res.add(tmp);
        }

        // 반환
        return res;

//        // Pagination 계산 처리
//        page = (page - 1) * count;
//
//        // Notice 불러오기
//        List<NoticeListResDto> noticeListPagination = new ArrayList<>();
//        List<Test> noticeList = noticeRepository.getNoticeList(count, page);
//        for (Test test : noticeList) {
//            NoticeListResDto res = NoticeListResDto.builder()
//                    .managerNo(test.getManagerNo())
//                    .noticeNo(test.getNoticeNo())
//                    .title(test.getTitle())
//                    .content(test.getContent())
//                    .createdAt(test.getCreatedAt())
//                    .updatedAt(test.getUpdatedAt())
//                    .build();
//            System.out.println(res);
//            noticeListPagination.add(res);
//        }
//
//        return noticeListPagination;
//        return noticeRepository.getNoticeList(count, page);
    }

    public NoticeDetailResDto getNoticeDetail(Long noticeNo) {

//        int theNumberOfNotice = noticeRepository.getTheNumberOfNotice();
//        System.out.println("theNumberOfNotice = " + theNumberOfNotice);

        // DB에서 데이터 Pagination 해서 불러오기
        Optional<Notice> notice = noticeRepository.findById(noticeNo);
        if (notice.isPresent()) {
            NoticeDetailResDto tmp = new NoticeDetailResDto();

            tmp.toEntity(notice.get());
            return tmp;
        } else {
            throw new IllegalArgumentException("존재하지 않는 공지사항입니다.");
        }

    }

    public int getNoticeCount() {

        int theNumberOfNotice = noticeRepository.getTheNumberOfNotice();

        return theNumberOfNotice;

    }

}
