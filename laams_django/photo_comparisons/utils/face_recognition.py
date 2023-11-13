import face_recognition


def face(existing_photo, new_photo):
    # 이미지 파일 로드
    image1 = face_recognition.load_image_file(existing_photo)
    image2 = face_recognition.load_image_file(new_photo)

    # 얼굴 인코딩
    encoding1 = face_recognition.face_encodings(image1)[0]
    encoding2 = face_recognition.face_encodings(image2)[0]

    # 얼굴 거리 계산
    face_distance = face_recognition.face_distance([encoding1], encoding2)[0]

    # 일치율을 백분율로 변환
    percentage_match = (1 - face_distance) * 100

    result = percentage_match, face_distance

    return result
