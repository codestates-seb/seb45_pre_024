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
              <div className="get">Get unstuck — ask a question</div>
            </div>
            <div className="suTextSmall">
              <div className="get">
                Unlock new privileges like voting and commenting
              </div>
            </div>
            <div className="suTextSmall">
              <div className="get">
                Save your favorite questions, answers, watch tags, and <br />
                more
              </div>
            </div>
            <div className="suTextSmall">
              <div className="get">Earn reputation and badges</div>
            </div>
            <div className="suTextDt">
              Collaborate and share knowledge with a private group for FREE.
              <br />
              <Link className="stack">
                Get Stack Overflow for Teams free for up to 50 users.
              </Link>
            </div>
          </div>
          <div className="suBox">
            <div className="suButtonContainer">
              <button className="suGoogle" onClick={handleGoogleSignIn}>
                <img
                  className="googleImg"
                  src={envURL + '/google.png'}
                  alt="google_logo"
                ></img>
                <span>Sign up with Google</span>
              </button>
              <button className="suGit" onClick={GithubLoginRequestHandler}>
                <img
                  className="githubImg"
                  src={envURL + '/github.png'}
                  alt="github_logo"
                ></img>
                <span>Sign up with Github</span>
              </button>
              <button className="suFacebook">
                <img
                  className="facebookImg"
                  src={envURL + '/facebook.png'}
                  alt="facebook_logo"
                ></img>
                <span>Sign up with Facebook</span>
              </button>
            </div>
            <div className="suInputContainer">
              <div className="suInput">
                <div className="suID">
                  <b>ID</b>
                  <div className="suIDCK">
                    <input
                      className="suInputId"
                      type="text"
                      value={id}
                      onChange={(e) => {
                        idHandle(e);
                      }}
                    />
                    <div className="checkCenter">
                      <button className="check">Check Availability</button>
                    </div>
                  </div>
                  <span className="errText">
                    {idErr
                      ? !succecsId
                        ? 'Please run a duplicate ID check.'
                        : 'ID must contain at least five characters, most 20 characters'
                      : null}
                  </span>
                </div>
                <div className="suEmail">
                  <b>Email</b>
                  <input
                    className="suInputDg"
                    type="text"
                    value={email}
                    onChange={(e) => {
                      emailHandle(e);
                    }}
                    onBlur={emailBlurHandle}
                  />
                  <span className={emailErr ? 'errText' : 'succecsText'}>
                    {succecsEmail
                      ? 'This is a valid email.'
                      : emailErr
                      ? 'Please enter a valid email address.'
                      : null}
                  </span>
                </div>
                <div className="suPwd">
                  <b>Password</b>
                  <input
                    className="suInputDg"
                    type="password"
                    value={pw}
                    onChange={(e) => {
                      pwHandle(e);
                    }}
                  />
                  <span className="errText">
                    {pwErr
                      ? 'Passwords must contain at least seven characters, including at least 1 letter, 1 number and 1 symbol.'
                      : null}
                  </span>
                </div>
                <div className="suPwd2">
                  <b>Confirm Password</b>
                  <div className="suPwdCon">
                    <input
                      className="suInputCp"
                      type="password"
                      value={verifyPw}
                      onChange={(e) => {
                        verifyPwHandle(e);
                      }}
                    />
                    <div className="verifyCenter">
                      <button className="verify" onClick={() => samePw()}>
                        Verify
                      </button>
                    </div>
                  </div>
                  <span className="errText">
                    {verifyPwErr ? 'Please check your Password.' : null}
                  </span>
                </div>
                <button
                  className="signupBtn"
                  onClick={() => submitHandle(id, email, pw)}
                >
                  Sign up
                </button>
                <div className="text2">
                  <p>
                    By clicking “Sign up”, you agree to our
                    <Link className="terms"> terms of service</Link> and
                    acknowledge that you have read and understand our
                    <Link className="terms"> privacy policy </Link>and
                    <Link className="terms"> code of conduct.</Link>
                  </p>
                </div>
              </div>
              <div className="txt">
                <div className="text3">
                  <p>
                    Already have an account?{' '}
                    <Link className="suLogin" to="/signin">
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
    </div>
  );
};

export default SignUp;
