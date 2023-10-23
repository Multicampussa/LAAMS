import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from "../Home/Home.jsx"
import Header from './Header.jsx';

const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<><Header/><Home /></>} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;