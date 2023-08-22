import PropTypes from 'prop-types';
import ReactHtmlParser from 'react-html-parser';
import './Answer.css';
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Wepediter from './Wepediter';
const Answer = ({ info, isLogin }) => {
  const [isDelete, setIsDelete] = useState(false);
  const [isFix, setIsFix] = useState(false);
  const navi = useNavigate();
  const deleteAnswer = () => {
    if (!isDelete) {
      setIsDelete(true);
    } else {
      const header = {
        headers: {
          Authorization: sessionStorage.getItem('authorization'),
          Refresh: sessionStorage.getItem('refresh'),
        },
      };
      axios
        .delete(`/answer/${info.answer_id}`, header)
        .then(navi('/'))
        .catch((res) => {
          expired_Access_token(
            res,
            'delete',
            '/answer',
            `/${info.answer_id}`,
            '',
            header.headers.Refresh,
          );
        });
    }
  };
  const expired_Access_token = (res, Method, URL, params, Data, refresh) => {
    if (res.response.data.message === 'Access token has expired') {
      sessionStorage.removeItem('authorization');
      sessionStorage.setItem(
        'authorization',
        res.response.headers.authorization,
      );
      const authorization = sessionStorage.getItem('authorization');
      axios[Method](`${URL}${params}`, Data, {
        headers: {
          authorization: authorization,
          refresh: refresh,
        },
      }).then(navi('/'));
    }
  };
  return (
    <div className="answerContainer">
      <div className={info.accepted ? `accepted` : ''}>
        <div className="username">Username : {info.username}</div>
        <div className="answer">
          Answer : {ReactHtmlParser(info.body)}{' '}
          {info.username === sessionStorage.getItem('username') && (
            <span>
              <button onClick={() => setIsFix(!isFix)}>수정하기</button>
              <button onClick={deleteAnswer}>삭제하기</button>
              {isDelete && (
                <span className="reqText">정말 삭제하시겠습니까?</span>
              )}
            </span>
          )}
        </div>
      </div>
      {isFix && (
        <Wepediter
          isLogin={isLogin}
          type={'FixAnswer'}
          answer_id={info.answer_id}
        />
      )}
    </div>
  );
};

Answer.propTypes = {
  info: PropTypes.object,
  isLogin: PropTypes.bool,
};
export default Answer;
