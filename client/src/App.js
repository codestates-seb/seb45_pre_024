import './App.css';
import QuestionList from './components/QuestionList';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import SignIn from './components/SignIn';
import { useEffect, useState } from 'react';
import CreateQuestion from './components/CreateQuestion';
import Footer from './components/footer';
import Header from './components/Header';
import SignUp from './components/SignUp';
import QuestionDetail from './components/QuestionDetail';
import { styled } from 'styled-components';
import UserList from './components/Users';
import SearchQuestion from './components/SearchQuestion';
import Oauth from './components/Oauth';
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
  const navi = useNavigate();
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const access_token = searchParams.get('access_token');
  access_token &&
    sessionStorage.setItem('authorization', 'Bearer ' + access_token);
  const refresh_token = searchParams.get('refresh_token');
  refresh_token && sessionStorage.setItem('refresh', refresh_token);
  const username = searchParams.get('username');
  username && sessionStorage.setItem('username', username);
  const email = searchParams.get('email');
  if (email) {
    sessionStorage.setItem('email', email);
    navi('/');
  }

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
      <Header isLogin={isLogin} logout={logoutHandle} />
      <Routes>
        <Route path="/" element={<QuestionList isLogin={isLogin} />} />
        <Route
          path="/questions/:question_id"
          element={<QuestionDetail isLogin={isLogin} user={user} />}
        />
        <Route
          path="/signin"
          element={<SignIn userHandle={userHandle} loginHandle={loginHandle} />}
        />
        <Route
          path="/create_question"
          element={<CreateQuestion user={user} isLogin={isLogin} />}
        />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/users" element={<UserList />} />
        <Route path="/search/:text" element={<SearchQuestion />} />
        <Route path="/oauth" element={<Oauth />} />
      </Routes>
      <Footer />
      <MouseStalker position={position} />
    </div>
  );
}

export default App;
