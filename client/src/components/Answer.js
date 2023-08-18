import PropTypes from 'prop-types';
import ReactHtmlParser from 'react-html-parser';

const Answer = ({ info }) => {
  return (
    <div className={info.accepted ? `accepted` : ''}>
      <div>유저 네임 : {info.username}</div>
      <div>답변 내용 : {ReactHtmlParser(info.body)}</div>
    </div>
  );
};

Answer.propTypes = {
  info: PropTypes.object,
};
export default Answer;
