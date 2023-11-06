package multicampussa.laams.home.notice.repository;

import multicampussa.laams.home.notice.domain.Notice;
import multicampussa.laams.home.notice.dto.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

//    @Query(value = "SELECT no AS noticeNo, title, content, created_at AS createdAt, updated_at AS updatedAt, manager_no AS managerNo FROM Notice ORDER BY noticeNo DESC", nativeQuery = true)

    /**
     * private Long noticeNo;
     * private Long managerNo;
     * <p>
     * private String title;
     * private String content;
     * private LocalDateTime createdAt;
     * private LocalDateTime updatedAt;
     */
//    ORDER BY Notice.no DESC LIMIT :count OFFSET :page
    @Query(value = "SELECT n.`no` as noticeNo, title, content, n.created_at as createdAt, n.updated_at as updatedAt, m.`no` as managerNo FROM notice n\n" +
            "left join manager m on n.manager_no = m.`no`\n" +
            "LIMIT 1, 10;", nativeQuery = true)
    List<Test> getNoticeList(@Param("count") int count, @Param("page") int page);

    @Query("select n from Notice n join fetch n.manager where n.isDelete = false order by n.createdAt desc")
    List<Notice> findNoticeWithManagerSortByCreatedAt(Pageable pageable);
    List<Notice> findAll();

    @Query("select count(*) from Notice")
    int getTheNumberOfNotice();
}
