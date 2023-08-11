import { useState, useEffect } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import { SHA256 } from 'crypto-js';
const SignIn = ({ user, userHandle, loginHandle }) => {
  const salt = 'salt';
  const [userData, setUserData] = useState(null);
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const GOOGLE_CLIENT_ID = process.env.REACT_APP_GOOGLE_CLIENT_ID;
  const navi = useNavigate();
  // Google 로그인 버튼을 클릭할 때 실행되는 함수
  const handleGoogleSignIn = () => {
    const redirectUri = 'http://localhost:3000/signin'; // 승인된 리디렉션 URI

    // Google OAuth URL 생성
    const authUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${redirectUri}&response_type=token&scope=email`;

    // Google 로그인 페이지로 리디렉션
    window.location.assign(authUrl);
  };

  // 엑세스 토큰을 사용하여 사용자 데이터를 가져오는 함수
  const fetchUserData = async (accessToken) => {
    try {
      // Google API 엔드포인트에 엑세스 토큰을 포함하여 요청을 보냄
      const response = await axios.get(
        'https://www.googleapis.com/oauth2/v2/userinfo',
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 헤더에 추가
          },
        },
      );
      setUserData(response.data); // 가져온 사용자 데이터를 상태에 저장
      userHandle(userData);
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
    if (id !== null) {
      axios
        .get(`http://localhost:3001/user`)
        .then((res) => {
          if (res.data[id].password === pw) {
            const bash = SHA256(pw + salt).toString();
            console.log(bash);
            loginHandle(res.data[id]);
            sessionStorage.setItem('user', JSON.stringify(res.data[id]));
            navi('/');
          }
        })
        .catch(console.error('err'));
    }
  };
  return (
    <div>
      <div>
        아이디
        <input
          type="text"
          value={id}
          onChange={(e) => {
            idHandle(e);
          }}
        />
        비밀번호
        <input
          type="password"
          value={pw}
          onChange={(e) => {
            pwHandle(e);
          }}
        />
      </div>
      <button onClick={handleGoogleSignIn}>Google 로그인</button>
      <button onClick={signInHandle}>로그인</button>
      {user && (
        <div>
          <h2>User Data</h2>
          <p>Email: {userData.email}</p>
        </div>
      )}
    </div>
  );
};
SignIn.propTypes = {
  user: PropTypes.object,
  userHandle: PropTypes.func,
  loginHandle: PropTypes.func,
};
export default SignIn;
