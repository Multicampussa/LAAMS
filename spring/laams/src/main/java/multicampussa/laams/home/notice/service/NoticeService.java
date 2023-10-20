package multicampussa.laams.home.notice.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.repository.MemberManagerRepository;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.dto.NoticeUpdateDto;
import multicampussa.laams.home.notice.repository.NoticeRepository;
import multicampussa.laams.manager.domain.manager.Manager;
import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException("접근 권한이 없습니다.");
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

    public boolean updateNotice(NoticeUpdateDto noticeUpdateDto, Long memberId) {

        boolean result = true;

        if (noticeUpdateDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        } else if (noticeUpdateDto.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
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

    public boolean deleteNotice(Long noticeNo, String authority) {

        boolean result = true;

        // 감독관은 공지사항 삭제할 수 없으니깐
        if (authority.equals("ROLE_DIRECTOR")) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        Optional<Notice> findNotice = noticeRepository.findById(noticeNo);

        if (findNotice.isEmpty()) {
            // 공지사항이 존재하지 않는 경우
            throw new IllegalArgumentException("존재하지 않는 공지사항입니다.");
        }
        noticeRepository.deleteById(noticeNo);

        return result;
    }
}
