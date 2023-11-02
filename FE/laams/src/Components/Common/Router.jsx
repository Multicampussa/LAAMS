import { BrowserRouter, Routes,Route } from 'react-router-dom';
import Home from "../Home/Home.jsx"
import DirectorHome from "../Director/Home/DirectorHome.jsx"
import PrivateRoute from './PrivateRoute.jsx';
import PublicRoute from './PublicRoute.jsx';
import ManagerHome from '../Manager/Home/ManagerHome.jsx';
import ManagerExamList from '../Manager/Exam/List.jsx';
import ManagerExamDetail from "../Manager/Exam/Detail.jsx";
import ManagerRewardList from "../Manager/Reward/List.jsx";
import ManagerErrorReportList from "../Manager/ErrorReport/List.jsx"
import Join from '../User/Join.jsx';
import UpdateUser from '../User/UpdateUser.jsx';
import Test from '../Test.jsx';
import ExamDetail from '../Director/Exam/ExamDetail';
import NoticeList from "../Notice/List.jsx";
import NoticeCreate from "../Notice/Create.jsx";
import NoticeDetail from "../Notice/Detail.jsx";
import Chart from '../Manager/Chart/Chart.jsx';
import DirectorChat from '../Director/Chat/DirectorChat.jsx';
const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/notice" element={<PublicRoute children={<NoticeList/>} />} />
          <Route
            path='/notice/create'
            element={
              <PrivateRoute role="manager" children={<NoticeCreate/>}></PrivateRoute>
            }
          />
          <Route
            path='/notice/detail/:noticeNo'
            element={
              <PublicRoute role="manager" children={<NoticeDetail/>}></PublicRoute>
            }
          />
          <Route
            path='/director'
            element={
              <PrivateRoute role="director" children={<DirectorHome/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager'
            element={
              <PrivateRoute role="manager" children={<ManagerHome/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/exam'
            element={
              <PrivateRoute role="manager" children={<ManagerExamList/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/exam/detail'
            element={
              <PrivateRoute role="manager" children={<ManagerExamDetail/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/error-report'
            element={
              <PrivateRoute role="manager" children={<ManagerErrorReportList/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/reward'
            element={
              <PrivateRoute role="manager" children={<ManagerRewardList/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/chart'
            element={
              <PrivateRoute role="manager" children={<Chart/>}></PrivateRoute>
            }
          />
          <Route path='/join' element={<Join/>} />
          <Route 
            path='/update/user' 
            element={
            <PrivateRoute role="director" children={<UpdateUser/>}></PrivateRoute>
            }
          />
          <Route 
            path='/director/exam/:no' 
            element={
            <PrivateRoute role="director" children={<ExamDetail/>}></PrivateRoute>
            }
          />
          <Route path='/test' element={<Test/>} />
          <Route
            path='/chat'
            element={
              <PrivateRoute role="director" children={<DirectorChat/>}></PrivateRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;