import boto3
from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from photo_comparisons.serializers import ComparisonLogSerializer
from photo_comparisons.utils.utils import upload_s3, create_response
from photo_comparisons.utils.face_recognition import face
from photo_comparisons.models import ComparisonLog

# Create your views here.
@api_view(['POST'])
def compare_photo(request):
    if request.method == 'POST' and request.FILES['existingPhoto'] and request.FILES['newPhoto']:
        existing_photo = request.FILES['existingPhoto']
        new_photo = request.FILES['newPhoto']

        examinee_name = request.POST.get('applicantName', '')  # 응시자 이름 가져오기'
        examinee_no = request.POST.get('applicantNo', '')  # 응시자 번호 가져오기

        # s3에 업로드
        existing_photo_url = upload_s3(existing_photo, f'{examinee_name}_기존')
        new_photo_url = upload_s3(new_photo, f'{examinee_name}_신규')

        # comparison_log 테이블에 저장
        comparison_log = ComparisonLog.objects.create(
            existing_photo=existing_photo_url,
            new_photo=new_photo_url,
            examinee_name=examinee_name,
            examinee_no=examinee_no
        )

        result = face(existing_photo, new_photo)

        # 성공 응답을 생성
        success_response = result
        return Response(success_response, status=status.HTTP_201_CREATED)