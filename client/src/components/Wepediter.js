import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/i18n/ko-kr';
import { useState, useRef } from 'react';
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
  FetchAnswerData,
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
      }
      const Data = {
        title: title,
        body: content,
      };
      axios
        .post(`/question`, Data, header)
        .then(navi('/'))
        .catch(console.log('err'));
    } else if (type === 'Answer') {
      const Data = {
        body: content,
      };
      axios
        .post(`/answer/${question_id}`, Data, header)
        .then(FetchAnswerData())
        .then(clearEditor)
        .catch((res) => {
          if (res.response.data.message === 'Forbidden request') {
            setErrRes('자신이 작성한 글에는 답변을 달 수 없습니다.');
          }
        });
    }
  };

  return (
    <div>
      {isLogin ? (
        <div>
          <Editor
            initialValue={content}
            previewStyle="tab"
            height={
              type === 'Question' ? '600px' : type === 'Answer' && '300px'
            }
            ref={editorRef}
            onChange={onChange}
            initialEditType="wysiwyg"
            language="ko-KR"
            placeholder="내용을 입력해주세요"
          />
          <div>
            <span></span>
            <button onClick={axiosData}>
              {type === 'Question'
                ? '질문 작성'
                : type === 'Answer' && '답변 작성'}
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
  user: PropTypes.object,
  type: PropTypes.string,
  errhandle: PropTypes.func,
  question_id: PropTypes.string,
  isLogin: PropTypes.bool,
  FetchAnswerData: PropTypes.func,
};
export default Wepediter;
