import { useState, useEffect } from 'react';
import './SignIn.css';
import axios from 'axios';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import { SHA256 } from 'crypto-js';
const envURL = process.env.PUBLIC_URL;
const SignIn = ({ loginHandle }) => {
  const salt = 'salt';
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const GOOGLE_CLIENT_ID = process.env.REACT_APP_GOOGLE_CLIENT_ID;
  const GITHUB_CLIENT_ID = process.env.REACT_APP_GITHUB_CLIENT_ID;
  const navi = useNavigate();

  const handleGoogleSignIn = () => {
    const redirectUri = 'http://localhost:3000/signin'; // 승인된 리디렉션 URI

    // Google OAuth URL 생성
    const authUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${redirectUri}&response_type=token&scope=email`;

    // Google 로그인 페이지로 리디렉션
    window.location.assign(authUrl);
  };
  const GithubLoginRequestHandler = () => {
    return window.location.assign(
      `https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}`,
    );
  };

  // 엑세스 토큰을 사용하여 사용자 데이터를 가져오는 함수
  const fetchUserData = async (accessToken) => {
    try {
      // Google API 엔드포인트에 엑세스 토큰을 포함하여 요청을 보냄
      const res = await axios.get(
        'https://www.googleapis.com/oauth2/v2/userinfo',
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 헤더에 추가
          },
        },
      );
      sessionStorage.setItem('user', JSON.stringify(res.data));
      loginHandle(res.data);
      navi('/');
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  // 페이지가 로드되면 URL의 해시 값을 파싱하고 사용자 데이터를 가져오는 효과 훅
  useEffect(() => {
    const hash = window.location.hash; // 현재 URL의 해시 값을 가져옴
    if (hash) {
      const accessToken = hash.split('=')[1].split('&')[0]; // 해시에서 엑세스 토큰 추출
      fetchUserData(accessToken); // 엑세스 토큰으로 사용자 데이터 가져옴
    }
  }, []);

  const idHandle = (e) => {
    setId(e.target.value);
  };
  const pwHandle = (e) => {
    setPw(e.target.value);
  };
  const signInHandle = () => {
    const data = {
      username: id,
      password: SHA256(pw + salt).toString(),
    };
    axios
      .post('/member/signin', data)
      .then((res) => {
        console.log(res.data);
        loginHandle(res.data);
        sessionStorage.setItem('user', JSON.stringify(res.data));
        navi('/');
      })
      .catch(console.error('err'));
  };
  return (
    <div>
      <div className="mainContainer">
        <div className="content">
          <div className="loginBox">
            <div className="logoContainer">
              <img className="logo" src={envURL + '/logo.png'} alt="logo"></img>
            </div>
            <div className="buttonContainer">
              <button className="google" onClick={handleGoogleSignIn}>
                <img
                  className="googleImg"
                  src={envURL + '/google.png'}
                  alt="google_logo"
                ></img>
                <span>Log in with Google</span>
              </button>
              <button className="git" onClick={GithubLoginRequestHandler}>
                <img
                  className="githubImg"
                  src={envURL + '/github.png'}
                  alt="github_logo"
                ></img>
                <span>Log in with Github</span>{' '}
              </button>
              <button className="facebook">Log in with Facebook</button>
            </div>
            <div className="inputContainer">
              <div className="input">
                <div className="id">
                  <b>ID</b>
                  <input
                    type="text"
                    value={id}
                    onChange={(e) => {
                      idHandle(e);
                    }}
                  />
                </div>
                <div className="pwd">
                  <b>Password</b>
                  <input
                    type="password"
                    value={pw}
                    onChange={(e) => {
                      pwHandle(e);
                    }}
                  />
                </div>

                <button className="loginBtn" onClick={signInHandle}>
                  Log in
                </button>
              </div>
              <div className="text">
                <p>Don’t have an account? Sign up</p>
                <div className="text2">
                  <p>Are you an employer? Sign up on Talent </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
SignIn.propTypes = {
  user: PropTypes.object,
  userHandle: PropTypes.func,
  loginHandle: PropTypes.func,
};
export default SignIn;
