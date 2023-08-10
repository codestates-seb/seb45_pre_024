import './App.css';
import QuestionList from './components/QuestionList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<QuestionList />} />
          <Route path="/signup" />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
