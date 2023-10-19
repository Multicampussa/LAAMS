package multicampussa.laams.home.notice.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.notice.controller.NoticeController;
import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.repository.NoticeRepository;
import multicampussa.laams.manager.domain.Manager;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NoticeService {

    public final NoticeRepository noticeRepository;
    public final ManagerRepository managerRepository;

    public void createNotice(NoticeCreateDto noticeCreateDto, Long memberId) {
        Notice notice = new Notice();
//        Manager manager = new Manager();
        Manager manager = managerRepository.findById(memberId);
        notice.update(noticeCreateDto, manager);
        noticeRepository.save(notice);
    }
}
