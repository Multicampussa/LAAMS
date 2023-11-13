package multicampussa.laams.home.notice.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    // 첨부파일 업로드
    public String saveFile(@RequestParam("file") MultipartFile file) {
        try {
//            String originalFilename = file.getOriginalFilename();

            // 파일명 중복 안되게 랜덤으로 이름 붙여줌
            String fileName = "notice/" + createFileName(file.getOriginalFilename());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // 업로드할 때 ACL 설정 추가
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3Client.putObject(putObjectRequest);

            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
    }

    public String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    public String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }


    // 첨부파일 다운로드
    public ResponseEntity<UrlResource> downloadFile(String originalFilename) throws IOException {
        UrlResource urlResource = new UrlResource(amazonS3Client.getUrl(bucket, originalFilename));

        String contentDisposition = "attachment; filename=\"" +  originalFilename + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);

    }



    // 첨부파일 삭제
    public void deleteFile(String originalFilename)  {
        amazonS3Client.deleteObject(bucket, originalFilename);
    }


}

