import './SignOn.css';
import { useState } from 'react';
const SignOn = () => {
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
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*[@$!%*?&])(?=.*\d)[A-Za-z@$!%*?&\d]{7,}$/i;

  const idHandle = (e) => {
    setId(e.target.value);
  };
  const pwHandle = (e) => {
    setPw(e.target.value);
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
      console.log('회원가입 성공');
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
    <div className="mainContainer">
      <div>
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
      <div>
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
      <div>
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
      <div>
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
      <button onClick={() => submitHandle(id, email, pw)}>submit</button>
    </div>
  );
};

export default SignOn;
