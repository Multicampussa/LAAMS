import { BrowserRouter, Routes,Route } from 'react-router-dom';
import Home from "../Home/Home.jsx"
import DirectorHome from "../Director/Home/DirectorHome.jsx"
import PrivateRoute from './PrivateRoute.jsx';
import ManagerHome from '../Manager/Home/ManagerHome.jsx';
import ManagerExamList from '../Manager/Exam/List.jsx';
import ManagerRewardList from "../Manager/Reward/List.jsx";
import ManagerErrorReportList from "../Manager/ErrorReport/List.jsx"
import Join from '../User/Join.jsx';
import UpdateUser from '../User/UpdateUser.jsx';
import Test from '../Test.jsx';
const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home/>} />
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
          <Route path='/join' element={<Join/>} />
          <Route path='/update/user' element={<PrivateRoute role="director" children={<UpdateUser/>}></PrivateRoute>}/>
          <Route path='/test' element={<Test/>} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;