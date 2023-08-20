import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/i18n/ko-kr';
import { useState, useRef } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const Wepediter = ({ title, user, type, errhandle }) => {
  const [content, setContent] = useState(' ');
  const editorRef = useRef();
  const navi = useNavigate();
  const onChange = () => {
    setContent(editorRef.current.getInstance().getHTML());
  };
  const axiosData = () => {
    if (title.length === 0 || title.length > 100) {
      errhandle('제목은 공란이거나 100글자 이상일 수 없습니다.');
    } else if (
      content === '' ||
      content === ' ' ||
      content === '<p><br></p>' ||
      content === '<p> </p>' ||
      content === '<p></p>'
    ) {
      errhandle('내용이 포함되어야 합니다.');
    } else if (type === 'Question') {
      const Data = {
        id: user.id,
        title: title,
        body: content,
      };
      axios.post(`/question`, Data).then(navi('/')).catch(console.log('err'));
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
        initialEditType="wysiwyg"
        language="ko-KR"
        placeholder="내용을 입력해주세요"
      />
      <button onClick={axiosData}>글 작성</button>
    </div>
  );
};

Wepediter.propTypes = {
  title: PropTypes.string,
  user: PropTypes.object,
  type: PropTypes.string,
  errhandle: PropTypes.func,
};
export default Wepediter;
