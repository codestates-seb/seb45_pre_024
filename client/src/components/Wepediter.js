import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { useState, useRef } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const Wepediter = ({ title, user, type }) => {
  const [content, setContent] = useState('내용을 입력해주세요');
  const editorRef = useRef();
  const navi = useNavigate();
  const onChange = () => {
    setContent(editorRef.current.getInstance().getHTML());
  };
  const axiosData = () => {
    const date = new Date();
    const time =
      date.getFullYear() +
      '-' +
      (date.getMonth() + 1) +
      '-' +
      date.getDate() +
      ' ' +
      date.getHours() +
      ':' +
      date.getMinutes() +
      ':' +
      date.getSeconds();
    if (type === 'Question') {
      const Data = {
        id: user.id,
        username: user.username,
        title: title,
        body: content,
        created_at: time,
      };
      axios
        .post(`http://localhost:3001/Question`, Data)
        .then(navi('/'))
        .catch(console.log('err'));
    }
  };
  return (
    <div>
      <Editor
        initialValue={content}
        previewStyle="tab"
        height="600px"
        ref={editorRef}
        onChange={onChange}
      />
      <button onClick={axiosData}>글 작성</button>
    </div>
  );
};

Wepediter.propTypes = {
  title: PropTypes.string,
  user: PropTypes.object,
  type: PropTypes.string,
};
export default Wepediter;
