import { BrowserRouter, Routes,Route } from 'react-router-dom';
import Home from "../Home/Home.jsx"
import DirectorHome from "../Director/Home/DirectorHome.jsx"
import PrivateRoute from './PrivateRoute.jsx';
import ManagerHome from '../Manager/Home/ManagerHome.jsx';
import ManagerExamList from '../Manager/Exam/List.jsx';
import Join from '../User/Join.jsx';
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
          <Route path='/join' element={<Join/>} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;