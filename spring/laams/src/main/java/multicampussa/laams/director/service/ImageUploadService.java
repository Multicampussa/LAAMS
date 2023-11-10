package multicampussa.laams.director.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.manager.domain.examinee.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private AmazonS3Client s3Client;
    private ImageRepository imageRepository;
    private ExamExamineeRepository examExamineeRepository;

    @Autowired
    public ImageUploadService(AmazonS3Client s3Client, ImageRepository imageRepository, ExamExamineeRepository examExamineeRepository) {
        this.s3Client = s3Client;
        this.imageRepository = imageRepository;
        this.examExamineeRepository = examExamineeRepository;
    }

    public String uploadImageToS3(MultipartFile file, byte[] imageBytes, String imageName, Long examNo, Long examineeNo, String imageReason) {
        // 시험_응시자 객체 불러오기
        ExamExaminee examInfo = examExamineeRepository.findByExamNoAndExamineeNo(examNo, examineeNo);

        // 응시자 수험 번호
        String examineeCode = examInfo.getExamineeCode();

        // 시험 날짜
        LocalDateTime examDate = examInfo.getExam().getExamDate();

        // S3에 저장할 경로 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm"); // 원하는 형식으로 포맷
        String formattedExamDate = examDate.format(formatter); // 형식에 따라 시험 날짜를 문자열로 변환

        String bucketName = "laams";
        String key = "images/" + formattedExamDate + "/" + examineeCode + "/" + imageName; // 경로 설정

        // 메타 데이터 설정(설정해주지 않으면 url 접속 시 바로 다운로드됨)
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, key, new ByteArrayInputStream(imageBytes), metadata);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putObjectRequest);

        String imageUrl = s3Client.getResourceUrl(bucketName, key);

        Image image = new Image();
        image.uploadImage(examInfo, imageUrl, imageReason);

        imageRepository.save(image);

        return imageUrl;
    }
}