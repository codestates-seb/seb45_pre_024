import PropTypes from 'prop-types';
import './Question.css';
import { Link } from 'react-router-dom';
const Question = ({ info }) => {
  const created_at =
    info.created_at.slice(0, 10) + ' ' + info.created_at.slice(11, 19);
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
