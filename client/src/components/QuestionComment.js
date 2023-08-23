import { useState } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import './QuestionComment.css';

const QuestionComment = ({ question_id, renderCurrentPage }) => {
  const [comment, setComment] = useState('');
  const [editedCommentId, setEditedCommentId] = useState(null); // Track edited comment

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

    {
      // Adding a new comment
      axios
        .post(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question/comment/${question_id}`,
          { body: comment },
          header,
        )
        .then(() => {
          setComment('');
          renderCurrentPage();
        });
    }
  };

  //   const handleEdit = (commentId, commentBody) => {
  //     setEditedCommentId(commentId);
  //     setComment(commentBody);
  //   };

  const handleDelete = (commentId) => {
    const header = {
      headers: {
        Authorization: sessionStorage.getItem('authorization'),
        Refresh: sessionStorage.getItem('refresh'),
      },
    };

    axios
      .delete(
        `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question/comment/${commentId}`,
        header,
      )
      .then(() => {
        renderCurrentPage();
      })
      .catch((error) => {
        console.error('Error deleting comment:', error);
      });
  };

  return (
    <div className="comment-form">
      <input
        className="comment_form"
        type="text"
        placeholder="댓글을 입력하세요..."
        value={comment}
        onChange={(e) => handleCommentChange(e)}
      />
      <button onClick={handleSubmit} className="comment_button">
        {editedCommentId ? '수정' : '등록'}
      </button>
      {editedCommentId && (
        <button
          onClick={() => setEditedCommentId(null)}
          className="comment_button"
        >
          취소
        </button>
      )}
      {editedCommentId && (
        <button
          onClick={() => handleDelete(editedCommentId)}
          className="comment_button"
        >
          삭제
        </button>
      )}
    </div>
  );
};

QuestionComment.propTypes = {
  question_id: PropTypes.string.isRequired,
  renderCurrentPage: PropTypes.func.isRequired,
};

export default QuestionComment;
