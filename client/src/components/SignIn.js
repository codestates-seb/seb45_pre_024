import { Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import './SignIn.css';
import axios from 'axios';
import PropTypes from 'prop-types';
const envURL = process.env.PUBLIC_URL;
const SignIn = ({ loginHandle }) => {
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const [oauthType, setOauthType] = useState(null);
  const GOOGLE_CLIENT_ID = process.env.REACT_APP_GOOGLE_CLIENT_ID;
  const GITHUB_CLIENT_ID = process.env.REACT_APP_GITHUB_CLIENT_ID;
  const navi = useNavigate();

  const handleGoogleSignIn = () => {
    const redirectUri = 'http://localhost:3000/signin'; // 승인된 리디렉션 URI

    // Google OAuth URL 생성
    const authUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${redirectUri}&response_type=code&scope=email`;

    // Google 로그인 페이지로 리디렉션
    setOauthType('google');
    window.location.assign(authUrl);
  };
  const GithubLoginRequestHandler = () => {
    setOauthType('github');
    return window.location.assign(
      `https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}`,
    );
  };

  // 엑세스 토큰을 사용하여 사용자 데이터를 가져오는 함수
  // const fetchUserData = async (accessToken) => {
  //   try {
  //     // Google API 엔드포인트에 엑세스 토큰을 포함하여 요청을 보냄
  //     const res = await axios.get(
  //       'https://www.googleapis.com/oauth2/v2/userinfo',
  //       {
  //         headers: {
  //           Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 헤더에 추가
  //         },
  //       },
  //     );
  //     sessionStorage.setItem('user', JSON.stringify(res.data));
  //     loginHandle(res.data);
  //     console.log(res.data);
  //     navi('/');
  //   } catch (error) {
  //     console.error('Error fetching user data:', error);
  //   }
  // };

  //리디렉션 후 실행
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const Authorization = params.get('code');
    const header = {
      headers: {
        'Authorization-code': `${oauthType} ${Authorization}`,
      },
    };
    if (Authorization) {
      console.log(Authorization);
      axios.post('./oauth', null, header).then((res) => {
        if (res.status === 200) {
          loginHandle(res.data);
          sessionStorage.setItem('username', res.data.username);
          sessionStorage.setItem('email', res.data.email);
          sessionStorage.setItem('authorization', res.headers['authorization']);
          sessionStorage.setItem('refresh', res.headers['refresh']);
          navi('/');
        } else if (res.status === 206) {
          sessionStorage.setItem('email', res.data.email);
          navi('/signIn');
        }
      });
    }
  }, []);

  const idHandle = (e) => {
    setId(e.target.value);
  };
  const pwHandle = (e) => {
    setPw(e.target.value);
  };
  const signInHandle = () => {
    const formData = new FormData();
    formData.append('username', id);
    formData.append('password', pw);
    const header = {
      header: {
        Authorization: 'Bearer your_access_token',
        'Content-Type': 'multipart/form-data',
      },
    };
    // const data = {
    //   username: id,
    //   password: pw,
    // };
    axios
      .post('/member/signin', formData, header)
      .then((res) => {
        loginHandle(res.data);
        sessionStorage.setItem('username', res.data.username);
        sessionStorage.setItem('email', res.data.email);
        sessionStorage.setItem('authorization', res.headers['authorization']);
        sessionStorage.setItem('refresh', res.headers['refresh']);
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
              <Link to="http://localhost:3000">
                <img className="logo" src={envURL + '/logo.png'} alt="logo" />
              </Link>
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
              <button className="facebook">
                <img
                  className="facebookImg"
                  src={envURL + '/facebook.png'}
                  alt="facebook_logo"
                ></img>
                <span>Log in with Facebook</span>
              </button>
            </div>
            <div className="inputContainer">
              <div className="input">
                <div className="id">
                  <b>ID</b>
                  <input
                    className="idInput"
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
                    className="pwdInput"
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
                <p>
                  Don’t have an account?{' '}
                  <Link className="signUp" to="/signup">
                    Sign up
                  </Link>
                </p>
                <div className="text2">
                  <p>
                    Are you an employer?{' '}
                    <Link
                      className="employer"
                      to="https://talent.stackoverflow.com/users/login"
                    >
                      Sign up on Talent{' '}
                    </Link>
                  </p>
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
