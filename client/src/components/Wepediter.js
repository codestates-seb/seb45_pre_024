import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/i18n/ko-kr';
import { useState, useRef, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Wepediter.css';
const Wepediter = ({
  title,
  type,
  errhandle,
  question_id,
  isLogin,
  renderCurrentPage,
  answer_id,
  init,
}) => {
  const [content, setContent] = useState(' ');
  const [errRes, setErrRes] = useState('');
  const editorRef = useRef();
  const navi = useNavigate();
  const onChange = () => {
    setContent(editorRef.current.getInstance().getHTML());
  };
  const clearEditor = () => {
    const editorInstance = editorRef.current.getInstance();
    editorInstance.setMarkdown('');
    setContent('');
  };
  const axiosData = () => {
    const authorization = sessionStorage.getItem('authorization');
    const refresh = sessionStorage.getItem('refresh');
    const header = {
      headers: {
        Authorization: authorization,
        Refresh: refresh,
      },
    };
    const expired_Access_token = (res, Method, URL, params, Data, navi) => {
      if (res.response.data.message === 'Access token has expired') {
        sessionStorage.removeItem('authorization');
        sessionStorage.setItem(
          'authorization',
          res.response.headers.authorization,
        );
        const authorization = sessionStorage.getItem('authorization');
        axios[Method](
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080${URL}${params}`,
          Data,
          {
            headers: {
              authorization: authorization,
              refresh: refresh,
            },
          },
        ).then(navi ? navi('/') : renderCurrentPage);
      }
    };
    if (
      content === '' ||
      content === ' ' ||
      content === '<p><br></p>' ||
      content === '<p> </p>' ||
      content === '<p></p>'
    ) {
      errhandle('내용이 포함되어야 합니다.');
    } else if (type === 'Question') {
      if (title.length === 0 || title.length > 100) {
        errhandle('제목은 공란이거나 100글자 이상일 수 없습니다.');
        return;
      }
      const Data = {
        title: title,
        body: content,
      };
      axios
        .post(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question`,
          Data,
          header,
        )
        .then(navi('/'))
        .catch((res) => {
          expired_Access_token(res, 'post', '/question', '', Data, navi('/'));
        });
    } else if (type === 'Answer') {
      const Data = {
        body: content,
      };
      axios
        .post(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/answer/${question_id}`,
          Data,
          header,
        )
        .then(renderCurrentPage())
        .then(clearEditor)
        .catch((res) => {
          if (res.response.data.message === 'Forbidden request') {
            setErrRes('자신이 작성한 글에는 답변을 달 수 없습니다.');
          }
          if (res.response.data.message === 'Access token has expired') {
            expired_Access_token(
              res,
              'post',
              '/answer',
              `/${question_id}`,
              Data,
            );
          }
        });
    } else if (type === 'FixAnswer') {
      const Data = {
        body: content,
      };
      axios
        .patch(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/answer/${answer_id}`,
          Data,
          header,
        )
        .then(renderCurrentPage())
        .catch((res) => {
          expired_Access_token(res, 'patch', '/answer', `/${answer_id}`, Data);
        });
    } else if (type === 'FixQuestion') {
      if (title.length === 0 || title.length > 100) {
        errhandle('제목은 공란이거나 100글자 이상일 수 없습니다.');
        return;
      }
      const Data = {
        title: title,
        body: content,
      };
      axios
        .patch(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question/${question_id}`,
          Data,
          header,
        )
        .then(renderCurrentPage())
        .catch((res) => {
          expired_Access_token(
            res,
            'patch',
            '/question',
            `/${question_id}`,
            Data,
          );
        });
    }
  };

  useEffect(() => {
    if (init) {
      setContent(init);
    }
  }, []);
  return (
    <div>
      {isLogin ? (
        <div>
          <Editor
            initialValue={init ? init : content}
            previewStyle="tab"
            height={
              type === 'Question'
                ? '600px'
                : type === 'Answer'
                ? '300px'
                : type === 'FixAnswer'
                ? '300px'
                : type === 'FixQuestion' && '550px'
            }
            ref={editorRef}
            onChange={onChange}
            initialEditType="wysiwyg"
            language="ko-KR"
            placeholder="내용을 입력해주세요"
          />
          <div>
            <span></span>
            <button onClick={axiosData} className="post_your_answer">
              {type === 'Question'
                ? 'Post Your Question'
                : type === 'Answer'
                ? 'Post Your Answer'
                : type === 'FixAnswer'
                ? 'Fix Your Answer'
                : type === 'FixQuestion' && 'Fix Your Question'}
            </button>{' '}
            {errRes && <span className="errRes">{errRes}</span>}
          </div>
        </div>
      ) : (
        <div>
          로그인한 유저만
          {`${type === 'Answer' ? '답변' : type === 'Question' && '질문'}`}을
          작성하실 수 있습니다
        </div>
      )}
    </div>
  );
};

Wepediter.propTypes = {
  title: PropTypes.string,
  type: PropTypes.string,
  errhandle: PropTypes.func,
  question_id: PropTypes.string,
  isLogin: PropTypes.bool,
  renderCurrentPage: PropTypes.func,
  answer_id: PropTypes.number,
  init: PropTypes.any,
};
export default Wepediter;
