import face_recognition

def face(existing_photo, new_photo):
    # 이미지 파일 로드
    image1 = face_recognition.load_image_file(existing_photo)
    image2 = face_recognition.load_image_file(new_photo)

    # 얼굴 위치 찾기
    face_locations1 = face_recognition.face_locations(image1)
    face_locations2 = face_recognition.face_locations(image2)

    if not face_locations1 or not face_locations2:
        raise ValueError("이미지에서 얼굴을 찾을 수 없습니다.")

    # 첫 번째 얼굴의 좌표를 사용
    face_location1 = face_locations1[0]
    face_location2 = face_locations2[0]

    # 얼굴 인코딩
    encoding1 = face_recognition.face_encodings(image1, [face_location1])[0]
    encoding2 = face_recognition.face_encodings(image2, [face_location2])[0]

    # 얼굴 거리 계산
    face_distance = face_recognition.face_distance([encoding1], encoding2)[0]

    # 일치율을 백분율로 변환
    percentage_match = (1 - face_distance) * 100

    result = percentage_match, face_distance

    return result
