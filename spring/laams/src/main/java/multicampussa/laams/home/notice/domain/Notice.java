package multicampussa.laams.home.notice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.dto.NoticeUpdateDto;
import multicampussa.laams.manager.domain.manager.Manager;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String title;

    @Column(length = 10000)
    private String content;

    // 첨부파일 S3 경로
    @Column(length = 1000)
    private String attachFile = "";

    // 첨부파일 명
    private String fileName = "";



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager_no")
    private Manager manager;

    private boolean isDelete = false;

    public void toEntity(NoticeCreateDto noticeCreateDto, Manager manager) {
        this.title = noticeCreateDto.getTitle();
        this.content = noticeCreateDto.getContent();
        this.manager = manager;
    }

    public void update(NoticeUpdateDto noticeUpdateDto) {
        this.title = noticeUpdateDto.getTitle();
        this.content = noticeUpdateDto.getContent();
    }

    public void delete() {
        this.isDelete = true;
    }

    public void setAttachFileUrl(String attachFileUrl) {
        this.attachFile = attachFileUrl;
    }

    public void setFileName(String attachFileName) {
        this.fileName = attachFileName;
    }


    @Override
    public String toString() {
        return "Notice{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachFile='" + attachFile + '\'' +
                ", createdAt=" + getCreatedAt() + '\'' +
                ", updatedAt=" + getUpdatedAt() + '\'' +
                '}';
    }
}
