import PropTypes from 'prop-types';
import './Question.css';
import { Link } from 'react-router-dom';
const Question = ({ info }) => {
  const created_at =
    info.created_at.slice(0, 10) + ' ' + info.created_at.slice(11, 19);
  return (
    <div>
      <section className="quBox">
        <div className="quStats">
          <div className="quVote">0 votes</div>
          <div className="quAnswer">{info.accepted} 0 answers</div>
          <div className="quView">{info.view_count} views</div>
        </div>
        <div className="quContent">
          <h3 className="quTitle">
            <Link to={`/questions/${info.question_id}`}>
              <span>{info.title}</span>
            </Link>
          </h3>
          <div className="quAuthor">
            <div className="quUser">{info.username}</div>
            <div className="quTime">asked {info.created_at} ago</div>
          </div>

          <div className="username">{info.username}</div>
          <div className="create_At">작성일:{created_at}</div>
        </div>
      </section>
    </div>
  );
};
Question.propTypes = {
  info: PropTypes.object,
};

export default Question;
