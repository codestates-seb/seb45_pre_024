import { useState } from 'react';
import Wepediter from './Wepediter';
import PropTypes from 'prop-types';
const CreateQuestion = ({ user }) => {
  const [textContent, setTextContent] = useState('');
  const textHandle = (e) => {
    setTextContent(e.target.value);
  };
  return (
    <div>
      <input
        type="text"
        placeholder="제목을 입력해주세요"
        value={textContent}
        onChange={(e) => {
          textHandle(e);
        }}
      ></input>
      <Wepediter title={textContent} user={user} type="Question" />;
    </div>
  );
};
CreateQuestion.propTypes = {
  user: PropTypes.object,
};
export default CreateQuestion;
