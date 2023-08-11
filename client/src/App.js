import './App.css';
import QuestionList from './components/QuestionList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SignIn from './components/SignIn';
import { useState } from 'react';
import Wepediter from './components/Wepediter';
function App() {
  const [user, setUser] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const userHandle = (e) => {
    setUser(e);
  };
  const loginHandle = () => {
    setIsLogin(true);
  };
  return (
    <div>
      {isLogin ? <div>로그인 완료</div> : null}
      {user ? <div>{user.email}님 안녕하세요</div> : null}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<QuestionList />} />
          <Route
            path="/signin"
            element={
              <SignIn
                user={user}
                userHandle={userHandle}
                loginHandle={loginHandle}
              />
            }
          />
          <Route path="/test" element={<Wepediter />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
