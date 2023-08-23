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
    axios
      .get(
        'http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question?page=1&size=10',
      )
      .then((res) => {
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
      axios
        .get(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question?page=${page}&size=10`,
        )
        .then((res) => {
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
      <div className="mainContent">
        <div className="line1">
          <h1 className="allQuestions">All questions</h1>
          {isLogin ? (
            <Link to="/create_question">
              <div className="questionBtn">
                <button className="ask_Question_Button">Ask Question</button>
              </div>
            </Link>
          ) : (
            <Link className="askBtn" to="/signin">
              <div className="buttonContiner">
                <button className="ask_Question_Button">Ask Question</button>
              </div>
            </Link>
          )}
        </div>
        <div className="line2">
          <div className="questionCount">{totalQuestions} questions</div>
          <div className="filterContainer">
            <button className="filterBtn1">Interesting</button>
            <button className="filterBtn2">Bountied</button>
            <button className="filterBtn3">Hot</button>
            <button className="filterBtn4" onClick={filterHandle}>
              Week
            </button>
            <button className="filterBtn5">Month</button>
          </div>
        </div>
        <div className="qtContainer">
          <div className="qtLiContainer">
            <div className="qtLiDiv">
              {questions.map((el) => {
                return <Question info={el} key={el.question_id} />;
              })}
              {isLoading ? <div>Loading...</div> : <div ref={bottom}></div>}
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
QuestionList.propTypes = {
  isLogin: PropTypes.bool,
};
export default QuestionList;
