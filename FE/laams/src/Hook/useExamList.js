
const useExamList = () => {
  const data = {};
  const res = [
    {
      "no": 1,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-26T09:52:22.119",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 2,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-26T09:52:39.776",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 3,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-02T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 4,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-04T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 5,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-05T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 6,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-06T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 7,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-07T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 8,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-08T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    },
    {
      "no": 9,
      "centerName": "SSAPIc서울SDA센터",
      "examDate": "2023-10-09T15:00:00",
      "managerNo": 1,
      "managerName": "admin",
      "examType": "SSAPIc",
      "examLanguage": "English"
    }
  ];

  res.forEach(e=>{
    const date = new Date(e.examDate);
    const temp = data[date.getDate()];
    if(temp){
      data[date.getDate()].push({
        centerName : e.centerName,
        examDate : new Date(e.examDate),
        managerName: e.managerName,
        examType : e.examType,
        examLanguage : e.examLanguage
      });
    }else{
      data[date.getDate()]=[{
        centerName : e.centerName,
        examDate : new Date(e.examDate),
        managerName: e.managerName,
        examType : e.examType,
        examLanguage : e.examLanguage
      }];
    }
  })
  return data;
}

export default useExamList;