import { useState } from 'react';
import Wepediter from './Wepediter';
import PropTypes from 'prop-types';
import './CreateQuestion.css';

const CreateQuestion = ({ user, isLogin }) => {
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
    <div className="mainContainer_create">
      <div className="question">Ask a public question</div>
      <div className="title_container">
        <div className="info_title">Title</div>
        <div className="info_detail">
          Be specific and imagine you’re asking a question to another person.
        </div>
        <input
          className="input_title"
          type="text"
          placeholder="Enter your question title"
          value={textContent}
          onChange={(e) => {
            textHandle(e);
          }}
        />
      </div>
      <Wepediter
        title={textContent}
        user={user}
        type="Question"
        errhandle={errhandle}
        isLogin={isLogin}
      />
      {isError && <div className="errText">{errText}</div>}
    </div>
  );
};

CreateQuestion.propTypes = {
  user: PropTypes.string,
  isLogin: PropTypes.bool,
};

export default CreateQuestion;
