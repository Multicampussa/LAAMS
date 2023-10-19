package multicampussa.laams.home.notice.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.repository.MemberManagerRepository;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.repository.NoticeRepository;
import multicampussa.laams.manager.domain.Manager;
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
}
