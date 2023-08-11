import './App.css';
import QuestionList from './components/QuestionList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SignIn from './components/SignIn';
import { useEffect, useState } from 'react';
import CreateQuestion from './components/CreateQuestion';
import Footer from './components/footer';
function App() {
  const [user, setUser] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    const sessionUser = sessionStorage.getItem('user');
    if (sessionUser) {
      setUser(JSON.parse(sessionUser));
    }
  }, []);
  const userHandle = (e) => {
    setUser(e);
  };
  const loginHandle = (e) => {
    setIsLogin(true);
    userHandle(e);
  };
  // const logoutHandle = () => {
  //   setIsLogin(false);
  //   sessionStorage.removeItem('user');
  // };
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
          <Route
            path="/create_Question"
            element={<CreateQuestion user={user} />}
          />
        </Routes>
      </BrowserRouter>
      <Footer />
    </div>
  );
}

export default App;
