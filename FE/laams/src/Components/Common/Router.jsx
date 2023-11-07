import { BrowserRouter, Routes,Route } from 'react-router-dom';
import Home from "../Home/Home.jsx"
import DirectorHome from "../Director/Home/DirectorHome.jsx"
import PrivateRoute from './PrivateRoute.jsx';
import PublicRoute from './PublicRoute.jsx';
import ManagerHome from '../Manager/Home/ManagerHome.jsx';
import ManagerExamList from '../Manager/Exam/List.jsx';
import ManagerExamDetail from "../Manager/Exam/Detail.jsx";
import ManagerCompensationList from "../Manager/Compensation/List.jsx";
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
import ManagerChat from '../Manager/Chat/ManagerChat.jsx';
import ManagerRoom from '../Manager/Chat/ManagerRoom.jsx';
import CreateErrorReport from '../Director/ErrorReport/CreateErrorReport.jsx';
import ManagerErrorReport from "../Manager/ErrorReport/Detail.jsx";
import CenterHome from '../CenterManager/CenterHome.jsx';
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
            path='/manager/error-report/:errorreportNo'
            element={
              <PrivateRoute role="manager" children={<ManagerErrorReport/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/compensation'
            element={
              <PrivateRoute role="manager" children={<ManagerCompensationList/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/chart'
            element={
              <PrivateRoute role="manager" children={<Chart/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/chat'
            element={
              <PrivateRoute role="manager" children={<ManagerChat/>}></PrivateRoute>
            }
          />
          <Route
            path='/manager/room'
            element={
              <PrivateRoute role="manager" children={<ManagerRoom/>}></PrivateRoute>
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
          <Route
            path='/director/chat'
            element={
              <PrivateRoute role="director" children={<DirectorChat/>}></PrivateRoute>
          }/>
          <Route 
            path='/director/create/error-report' 
            element={
            <PrivateRoute role="director" children={<CreateErrorReport/>}></PrivateRoute>
            }
          />
          <Route
            path='/centermanager'
            element={
              <PrivateRoute role="centermanager" children={<CenterHome/>}></PrivateRoute>
            }
          />
          <Route path='/test' element={<Test/>} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;