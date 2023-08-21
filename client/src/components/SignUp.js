import './SignUp.css';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const envURL = process.env.PUBLIC_URL;
const SignUp = () => {
  const [id, setId] = useState('');
  const [possibleId, setPossibleId] = useState(false);
  const [idMessage, setIdMessage] = useState(
    'Please run a duplicate ID check.',
  );
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
  const [succecsSamePw, setSuccecsSamePw] = useState(false);
  const navi = useNavigate();

  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*[@$!%*?&])(?=.*\d)[A-Za-z@$!%*?&\d]{7,}$/i;

  const oauthHandle = (type) => {
    window.location.assign(
      `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/${type}`,
    );
  };

  const idHandle = (e) => {
    setId(e.target.value);
    setSuccecsId(false);
    setPossibleId(false);
  };

  const idCheck = () => {
    if (4 < id.length && id.length < 21) {
      axios.post('/member/check/username', { username: id }).then((res) => {
        if (res.data.is_duplicated) {
          setIdErr(true); // 응답이 ture면 idErr true 나머지 성공 false
          setSuccecsId(false);
          setPossibleId(false);
          setIdMessage('This ID is already in use.');
        } else {
          setIdErr(false); // 응답이 false면 idErr false 나머지 성공 true
          setSuccecsId(true);
          setPossibleId(true);
          setIdMessage('This ID is available.');
        }
      });
    } else {
      setIdErr(true); //글자수 조건 안맞으니 idErr true 나머지 성공 false
      setSuccecsId(false);
      setPossibleId(false);
      setIdMessage(
        'ID must contain at least five characters, most 20 characters',
      );
    }
    console.log(possibleId);
  };

  const pwHandle = (e) => {
    setSuccecsSamePw(false);
    setPw(e.target.value);
    if (!passwordRegex.test(e.target.value)) {
      setSuccecsPw(false);
      setPwErr(true);
    } else {
      setSuccecsPw(true);
      setPwErr(false);
    }
  };

  const verifyPwHandle = (e) => {
    setVerifyPw(e.target.value);
  };

  const emailBlurHandle = () => {
    if (!emailRegex.test(email)) {
      setEmailErr(true);
      setSuccecsEmail(false);
    } else {
      setEmailErr(false);
      setSuccecsEmail(true);
    }
  };

  const emailHandle = (e) => {
    setEmail(e.target.value);
    if (!emailRegex.test(e.target.value)) {
      setEmailErr(true);
      setSuccecsEmail(false);
    } else {
      setEmailErr(false);
      setSuccecsEmail(true);
    }
  };

  const submitHandle = (id, email, pw) => {
    if (succecsId && succecsEmail && succecsPw && succecsSamePw) {
      const data = {
        username: id,
        email: email,
        password: pw,
      };
      axios.post('/member/signup', data).then((res) => {
        console.log(res);
        if (res.status === 201) {
          navi('/signin');
        }
      });
    }
  };
  const samePw = () => {
    if (pw === verifyPw && succecsPw) {
      setSuccecsSamePw(true);
      setVerifyPwErr(false);
    } else {
      setSuccecsSamePw(false);
      setVerifyPwErr(true);
    }
    console.log(succecsId, succecsEmail, succecsPw, succecsSamePw);
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
              <button
                className="suGoogle"
                onClick={() => oauthHandle('google')}
              >
                <img
                  className="googleImg"
                  src={envURL + '/google.png'}
                  alt="google_logo"
                ></img>
                <span>Sign up with Google</span>
              </button>
              <button className="suGit" onClick={() => oauthHandle('github')}>
                <img
                  className="githubImg"
                  src={envURL + '/github.png'}
                  alt="github_logo"
                ></img>
                <span>Sign up with Github</span>
              </button>
              <button
                className="suFacebook"
                onClick={() => oauthHandle('facebook')}
              >
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
                      <button className="check" onClick={idCheck}>
                        Check Availability
                      </button>
                    </div>
                  </div>
                  <span className={idErr ? 'errText' : 'succecsText'}>
                    {idMessage}
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
