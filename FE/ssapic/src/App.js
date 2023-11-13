import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from "./Page/Home/Home.jsx";
import ExamHome from "./Page/Exam/Home.jsx";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/exam" element={<ExamHome />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
