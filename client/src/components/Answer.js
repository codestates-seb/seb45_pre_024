import PropTypes from 'prop-types';
import ReactHtmlParser from 'react-html-parser';
import './Answer.css';
const Answer = ({ info }) => {
  return (
    <div className="answerContainer">
      <div className={info.accepted ? `accepted` : ''}>
        <div className="username">Username : {info.username}</div>
        <div className="answer">Answer : {ReactHtmlParser(info.body)}</div>
      </div>
    </div>
  );
};

Answer.propTypes = {
  info: PropTypes.object,
};
export default Answer;
