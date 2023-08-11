import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { useState, useRef } from 'react';
const Wepediter = () => {
  const [content, setContent] = useState('');
  const editorRef = useRef();
  const onChange = () => {
    setContent(editorRef.current.getInstance().getHTML());
    console.log(content);
  };
  const consoleData = () => {
    console.log(content);
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
      <button onClick={consoleData}>글 작성</button>
    </div>
  );
};

export default Wepediter;
