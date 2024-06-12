package multicampussa.laams.home.notice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import multicampussa.laams.global.BaseTimeEntity;
import multicampussa.laams.home.member.domain.Member;
import multicampussa.laams.home.notice.dto.NoticeCreateDto;
import multicampussa.laams.home.notice.dto.NoticeUpdateDto;

import javax.persistence.*;

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
    @Column(length = 1000)
    private String fileName = "";



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager_no")
    private Member member;

    private boolean isDelete = false;

    public void toEntity(NoticeCreateDto noticeCreateDto, Member member) {
        this.title = noticeCreateDto.getTitle();
        this.content = noticeCreateDto.getContent();
        this.member = member;
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
