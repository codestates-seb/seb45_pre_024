import './App.css';
import QuestionList from './components/QuestionList';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import SignIn from './components/SignIn';
import { useEffect, useState } from 'react';
import CreateQuestion from './components/CreateQuestion';
import Footer from './components/footer';
import Header from './components/Header';
import SignOn from './components/SignOn';
// import LeftSidebar from './components/LeftSideBar';
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
  const logoutHandle = () => {
    setUser(null);
    setIsLogin(false);
    sessionStorage.removeItem('user');
  };
  return (
    <div>
      <BrowserRouter>
        <Header isLogin={isLogin} logout={logoutHandle} />
        {/* <span className="leftside">
          <LeftSidebar />
        </span> */}
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
