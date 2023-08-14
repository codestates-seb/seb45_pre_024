import './App.css';
import QuestionList from './components/QuestionList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SignIn from './components/SignIn';
import { useEffect, useState } from 'react';
import CreateQuestion from './components/CreateQuestion';
import Footer from './components/footer';
import Header from './components/Header';
import SignOn from './components/SignOn';
import LeftSidebar from './components/LeftSideBar';
function App() {
  const [user, setUser] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  useEffect(() => {
    const sessionUser = sessionStorage.getItem('user');
    if (sessionUser) {
      setUser(JSON.parse(sessionUser));
      setIsLogin(true);
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
        <Header />
        <LeftSidebar />
        <Routes>
          <Route path="/" element={<QuestionList isLogin={isLogin} />} />
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
            path="/create_question"
            element={<CreateQuestion user={user} />}
          />
          <Route path="/signon" element={<SignOn />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;
