import { useState } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

const QuestionComment = ({ question_id, renderCurrentPage }) => {
  const [comment, setComment] = useState('');

  const handleCommentChange = (event) => {
    setComment(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (comment.trim() === '') {
      return;
    }

    const header = {
      headers: {
        Authorization: sessionStorage.getItem('authorization'),
        Refresh: sessionStorage.getItem('refresh'),
      },
    };

    axios
      .post(`/answer/${question_id}`, { body: comment }, header)
      .then(() => {
        setComment('');
        renderCurrentPage();
      })
      .catch((error) => {
        console.error('Error posting comment:', error);
      });
  };

  return (
    <div className="comment-form">
      <textarea
        rows="4"
        cols="50"
        placeholder="댓글을 입력하세요..."
        value={comment}
        onChange={handleCommentChange}
      ></textarea>
      <button onClick={handleSubmit}>댓글 작성</button>
    </div>
  );
};

QuestionComment.propTypes = {
  question_id: PropTypes.string.isRequired,
  renderCurrentPage: PropTypes.func.isRequired,
};

export default QuestionComment;
