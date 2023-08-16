import PropTypes from 'prop-types';
import './Question.css';
import { Link } from 'react-router-dom';
const Question = ({ info }) => {
  return (
    <div>
      <section className="container">
        <div>
          <div className="title">
            <Link to={`/questions/${info.question_id}`}>
              <span>{info.title}</span>
            </Link>
          </div>

          <div className="username">{info.username}</div>
          <div className="create_At">작성일:{info.created_at}</div>
        </div>
      </section>
    </div>
  );
};
Question.propTypes = {
  info: PropTypes.object,
};

export default Question;
