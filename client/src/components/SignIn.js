import { Link, useNavigate } from 'react-router-dom';
import { useState, useRef } from 'react';
import './SignIn.css';
import axios from 'axios';
import PropTypes from 'prop-types';
const envURL = process.env.PUBLIC_URL;
const SignIn = ({ loginHandle }) => {
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const [loginErr, setLoginErr] = useState(false);
  const navi = useNavigate();
  const inputRef = useRef(null);

  const oauthHandle = (type) => {
    window.location.assign(
      `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/${type}`,
    );
  };

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
    axios
      .post(
        'http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/member/signin',
        formData,
        header,
      )
      .then((res) => {
        loginHandle(res.data);
        sessionStorage.setItem('username', res.data.username);
        sessionStorage.setItem('email', res.data.email);
        sessionStorage.setItem('authorization', res.headers['authorization']);
        sessionStorage.setItem('refresh', res.headers['refresh']);
        navi('/');
      })
      .catch((res) => {
        if (res.response.data.message === 'Unauthorized') setLoginErr(true);
      });
  };
  const enterHandle = (e) => {
    console.log(e);
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
              <button className="google" onClick={() => oauthHandle('google')}>
                <img
                  className="googleImg"
                  src={envURL + '/google.png'}
                  alt="google_logo"
                ></img>
                <span>Log in with Google</span>
              </button>
              <button className="git" onClick={() => oauthHandle('github')}>
                <img
                  className="githubImg"
                  src={envURL + '/github.png'}
                  alt="github_logo"
                ></img>
                <span>Log in with Github</span>{' '}
              </button>
              <button
                className="facebook"
                onClick={() => oauthHandle('facebook')}
              >
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
                    ref={inputRef}
                    onKeyUp={(e) => enterHandle(e)}
                  />
                </div>
                {loginErr && (
                  <span className="errText">
                    ID혹은 비밀번호를 확인해주세요
                  </span>
                )}
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
  userHandle: PropTypes.func,
  loginHandle: PropTypes.func,
};
export default SignIn;
