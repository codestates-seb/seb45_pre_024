import './SignUp.css';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const envURL = process.env.PUBLIC_URL;
const SignUp = () => {
  const [id, setId] = useState('');
  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');
  const [verifyPw, setVerifyPw] = useState('');
  const [idErr, setIdErr] = useState(false);
  const [emailErr, setEmailErr] = useState(false);
  const [pwErr, setPwErr] = useState(false);
  const [verifyPwErr, setVerifyPwErr] = useState(false);
  const [succecsId, setSuccecsId] = useState(false);
  const [succecsEmail, setSuccecsEmail] = useState(false);
  const [succecsPw, setSuccecsPw] = useState(false);
  const navi = useNavigate();

  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*[@$!%*?&])(?=.*\d)[A-Za-z@$!%*?&\d]{7,}$/i;

  // 0816_수정한(추가된) 부분(33번줄까지)//
  const GOOGLE_CLIENT_ID = process.env.REACT_APP_GOOGLE_CLIENT_ID;
  const GITHUB_CLIENT_ID = process.env.REACT_APP_GITHUB_CLIENT_ID;
  const handleGoogleSignIn = () => {
    const redirectUri = 'http://localhost:3000/signin';
    const authUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${redirectUri}&response_type=token&scope=email`;
    window.location.assign(authUrl);
  };
  const GithubLoginRequestHandler = () => {
    return window.location.assign(
      `https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}`,
    );
  };

  const idHandle = (e) => {
    setId(e.target.value);
    setSuccecsId(false);
  };
  const pwHandle = (e) => {
    setPw(e.target.value);
    setSuccecsPw(false);
  };
  const verifyPwHandle = (e) => {
    setVerifyPw(e.target.value);
  };
  const errHandle = (type) => {
    if (type === 'id') {
      setIdErr(true);
    }
    if (type === 'email') {
      setEmailErr(true);
    }
    if (type === 'pw') {
      setPwErr(true);
    }
  };
  const emailBlurHandle = () => {
    if (!emailRegex.test(email)) {
      errHandle('email');
      setSuccecsEmail(false);
    } else {
      setEmailErr(false);
      setSuccecsEmail(true);
    }
  };
  const emailHandle = (e) => {
    setEmail(e.target.value);
    if (!emailRegex.test(email)) {
      errHandle('email');
    } else {
      setEmailErr(false);
      setSuccecsEmail(true);
    }
  };
  const submitHandle = (id, email, pw) => {
    if (id.length > 20 || id.length < 5) {
      errHandle('id');
    } else {
      setIdErr(false);
      setSuccecsId(true);
    }
    if (!emailRegex.test(email)) {
      errHandle('email');
    } else {
      setEmailErr(false);
      setSuccecsEmail(true);
    }
    if (!passwordRegex.test(pw)) {
      errHandle('pw');
    } else {
      setPwErr(false);
      setSuccecsPw(true);
    }
    if (succecsId && succecsEmail && succecsPw) {
      const data = {
        username: id,
        email: email,
        password: pw,
      };
      console.log(data);
      axios.post('/member/signup', data).then((res) => {
        console.log(res);
        if (res.status === 201) {
          navi('/signin');
        }
      });
    }
  };
  const samePw = () => {
    if (pw === verifyPw) {
      setSuccecsPw(true);
      setVerifyPwErr(false);
    } else {
      setSuccecsPw(false);
      setVerifyPwErr(true);
    }
  };
  return (
    <div className="suMainContainer">
      <div className="suContent">
        <div className="suCenter">
          <div className="suText">
            <h1 className="join">Join the Stack Overflow community</h1>
            <div className="suTextSmall">
              <p>Get unstuck — ask a question</p>
            </div>
            <div className="suTextSmall">
              <p>Unlock new privileges like voting and commenting</p>
            </div>
            <div className="suTextSmall">
              <p>Save your favorite questions, answers, watch tags, and more</p>
            </div>
            <div className="suTextSmall">
              <p>Earn reputation and badges</p>
            </div>
            <p className="suTextDt">
              Collaborate and share knowledge with a private group for FREE.
              <br />
              Get Stack Overflow for Teams free for up to 50 users.
            </p>
          </div>
          <div className="suBox">
            <div className="suButtonContainer">
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
                <span>Log in with Github</span>
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
            <div className="suInputContainer">
              <div className="suInput">
                <div className="suID">
                  아이디
                  <input
                    type="text"
                    value={id}
                    onChange={(e) => {
                      idHandle(e);
                    }}
                  />
                  <button>중복확인</button>
                  <span className="errText">
                    {idErr
                      ? !succecsId
                        ? '아이디 중복검사를 실행해 주세요'
                        : '아이디는 5글자 이상 20글자 이하여야 합니다.'
                      : null}
                  </span>
                </div>
                <div className="suEmail">
                  이메일
                  <input
                    type="text"
                    value={email}
                    onChange={(e) => {
                      emailHandle(e);
                    }}
                    onBlur={emailBlurHandle}
                  />
                  <span className={emailErr ? 'errText' : 'succecsText'}>
                    {succecsEmail
                      ? '올바른 이메일 형식입니다.'
                      : emailErr
                      ? '유효하지 않은 이메일 형식입니다.'
                      : null}
                  </span>
                </div>
                <div className="suPwd">
                  비밀번호
                  <input
                    type="password"
                    value={pw}
                    onChange={(e) => {
                      pwHandle(e);
                    }}
                  />
                  <span className="errText">
                    {pwErr
                      ? '비밀번호는 영단어,숫자,특수문자가 1글자 이상씩 포함되어야 하며 7글자 이상이여야 합니다.'
                      : null}
                  </span>
                </div>
                <div className="suPwd2">
                  비밀번호 확인
                  <input
                    type="password"
                    value={verifyPw}
                    onChange={(e) => {
                      verifyPwHandle(e);
                    }}
                  />
                  <button onClick={() => samePw()}>비밀번호 확인</button>
                  <span className="errText">
                    {verifyPwErr ? '비밀번호를 확인해주세요.' : null}
                  </span>
                </div>
                <button
                  className="signupBtn"
                  onClick={() => submitHandle(id, email, pw)}
                >
                  Sign up
                </button>
                <div className="text2">
                  <p>ddd</p>
                </div>
              </div>
              <div className="text3">
                <p>
                  Already have an account?{' '}
                  <Link className="suLogin" to="http://localhost:3000/signin">
                    Log in
                  </Link>
                </p>
              </div>
              <div className="text4">
                <p>
                  Are you an employer?{' '}
                  <Link
                    className="suEmployer"
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
  );
};

export default SignUp;
