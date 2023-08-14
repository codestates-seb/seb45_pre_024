import { useState } from 'react';
import Wepediter from './Wepediter';
import PropTypes from 'prop-types';
import './CreateQuestion.css';
const CreateQuestion = ({ user }) => {
  const [textContent, setTextContent] = useState('');
  const [isError, setIsError] = useState(false);
  const [errText, setErrText] = useState('');
  const textHandle = (e) => {
    setTextContent(e.target.value);
  };
  const errhandle = (e) => {
    setIsError(true);
    setErrText(e);
  };
  return (
    <div className="mainContainer">
      <input
        type="text"
        placeholder="제목을 입력해주세요"
        value={textContent}
        onChange={(e) => {
          textHandle(e);
        }}
      ></input>
      <Wepediter
        title={textContent}
        user={user}
        type="Question"
        errhandle={errhandle}
      />
      {isError ? <div className="errText">{errText}</div> : null}
    </div>
  );
};
CreateQuestion.propTypes = {
  user: PropTypes.object,
};
export default CreateQuestion;
