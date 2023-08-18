import { useState, useEffect, useCallback, useRef } from 'react';
import axios from 'axios';
import Question from './Question';
import './QuestionList.css';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
const QuestionList = ({ isLogin }) => {
  const [questions, setQuestions] = useState([]);
  const [page, setPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [filter, setFilter] = useState('');
  const [maxPage, setMaxPage] = useState(null);
  const [totalQuestions, setTotalQuestions] = useState(0);
  const bottom = useRef(null);
  useEffect(() => {
    axios.get('/question?page=1&size=10').then((res) => {
      setQuestions(res.data.data);
      setPage(page + 1);
      setIsLoading(false);
      setMaxPage(res.data.pageInfo.totalPages);
      setTotalQuestions(res.data.pageInfo.totalElements);
    });
  }, []);

  const renderNextPage = useCallback(() => {
    if (page <= maxPage) {
      setIsLoading(true);
      axios.get(`/question?page=${page}&size=10`).then((res) => {
        setQuestions(questions.concat(res.data.data));
        setPage(page + 1);
        setIsLoading(false);
      });
    }
  }, [page, questions]);

  useEffect(() => {
    if (bottom.current) {
      const observer = new IntersectionObserver(
        (entries) => {
          if (entries[0].isIntersecting) {
            renderNextPage();
          }
        },
        {
          threshold: 1,
        },
      );
      observer.observe(bottom.current);
      return () => observer.disconnect();
    }
  }, [renderNextPage]);

  const filterHandle = (e) => {
    setFilter(e);
    console.log(filter);
  };
  return (
    <section className="mainContiner">
      <div className="buttonContiner">
        <div className="QuestionLength">{totalQuestions}개의 질문</div>
        {isLogin ? (
          <Link to="/create_question">
            <button className="ask_Question_Button">Ask Question</button>
          </Link>
        ) : (
          <Link to="/signin">
            <button className="ask_Question_Button">Ask Question</button>
          </Link>
        )}
      </div>
      <div className="filterButtonContiner">
        <button className="filterBtn" onClick={filterHandle}>
          Week
        </button>
        <button className="filterBtn">Month</button>
      </div>
      <div>
        {questions.map((el) => {
          return <Question info={el} key={el.question_id} />;
        })}
        {isLoading ? <div>Loading...</div> : <div ref={bottom}></div>}
      </div>
    </section>
  );
};
QuestionList.propTypes = {
  isLogin: PropTypes.bool,
};
export default QuestionList;
