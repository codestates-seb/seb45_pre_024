import './App.css';
import QuestionList from './components/QuestionList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SignIn from './components/SignIn';
import { useEffect, useState } from 'react';
import CreateQuestion from './components/CreateQuestion';
import Footer from './components/footer';
import Header from './components/Header';
import SignUp from './components/SignUp';
import QuestionDetail from './components/QuestionDetail';
import LeftSidebar from './components/LeftSideBar';
import { styled } from 'styled-components';
import UserList from './components/Users';
import SearchQuestion from './components/SearchQuestion';
const MouseStalker = styled.div`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: hsla(0, 0%, 75%, 0.2);
  position: fixed;
  left: ${(props) => props.position.x - 20}px;
  top: ${(props) => props.position.y - 20}px;
  pointer-events: none;
  border: 1px solid #aaa;
`;

function App() {
  const [user, setUser] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const [position, setPosition] = useState({ x: 0, y: 0 });

  useEffect(() => {
    const sessionUser = sessionStorage.getItem('username');
    if (sessionUser) {
      setUser(sessionUser);
      setIsLogin(true);
    }
  }, []);

  useEffect(() => {
    const handleMouseMove = (event) => {
      setPosition({ x: event.clientX, y: event.clientY });
    };

    window.addEventListener('mousemove', handleMouseMove);

    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
    };
  }, []);

  const userHandle = (e) => {
    setUser(e);
  };

  const loginHandle = (e) => {
    setIsLogin(true);
    userHandle(e);
  };

  const logoutHandle = () => {
    setUser(null);
    setIsLogin(false);
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('authorization');
    sessionStorage.removeItem('refresh');
  };

  return (
    <div>
      <BrowserRouter>
        <Header isLogin={isLogin} logout={logoutHandle} />
        <span className="leftside">
          <LeftSidebar />
        </span>
        <Routes>
          <Route path="/" element={<QuestionList isLogin={isLogin} />} />
          <Route
            path="/questions/:question_id"
            element={<QuestionDetail isLogin={isLogin} user={user} />}
          />
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
            element={<CreateQuestion user={user} isLogin={isLogin} />}
          />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/search/:text" element={<SearchQuestion />} />
        </Routes>
        <Footer />
      </BrowserRouter>
      <MouseStalker position={position} />
    </div>
  );
}

export default App;
