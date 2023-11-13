from botocore.exceptions import NoCredentialsError
from decouple import config
import boto3


def upload_s3(photo, examinee_name):
    # AWS 자격 증명 설정
    access_key = config('S3_ACCESS_KEY_ID')
    secret_key = config('S3_SECRET_ACCESS_KEY')
    service_name = 's3'
    endpoint_url = 'https://kr.object.ncloudstorage.com'

    # S3 클라이언트 생성
    s3 = boto3.client(service_name,
                      endpoint_url=endpoint_url,
                      aws_access_key_id=access_key,
                      aws_secret_access_key=secret_key
                      )

    # 업로드할 이미지 파일명과 S3 버킷 이름 설정
    # 네이버 객체 스토리지에 model_name라는 경로에 parsed_name라는 이름으로 생성됨
    image_file_name = examinee_name
    bucket_name = 'laams'
    object_key = f'examinee/{examinee_name}'

    # 파일을 바이트 스트림으로 읽고 S3 버킷에 업로드
    try:
        s3.put_object(
            Bucket=bucket_name,
            Key=f'examinee/{examinee_name}',
            Body=photo,
            ACL='public-read',
            ContentType='image/jpeg'
        )
        file_url = f"https://kr.object.ncloudstorage.com/{bucket_name}/{object_key}"

        return file_url

    except NoCredentialsError:
        print("AWS 자격 증명이 없습니다.")
        return None

    except Exception as e:
        print(f"파일 업로드 중 오류 발생: {e}")
        return None