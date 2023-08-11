import { useState, useEffect, useCallback, useRef } from 'react';
import axios from 'axios';
import Question from './Question';
import './QuestionList.css';
import { Link } from 'react-router-dom';
const QuestionList = () => {
  const [questions, setQuestions] = useState([]);
  const [currentQuestions, setCurrentQuestions] = useState([]);
  const [page, setPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [filter, setFilter] = useState('');
  const bottom = useRef(null);
  // const APIURL = "http://3.39.152.190:8080/"
  useEffect(() => {
    axios
      .get(`http://localhost:3001/Question${filter}`)
      .then((res) => {
        setQuestions(res.data);
        setCurrentQuestions(res.data.slice(0, 10 * page));
        setIsLoading(false);
      })
      .catch(console.log('err'));
  }, []);

  const renderNextPage = useCallback(() => {
    setIsLoading(true);
    if (page < 10) {
      setCurrentQuestions(questions.slice(0, 10 * (page + 1)));
      setPage(page + 1);
    }
    setIsLoading(false);
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
  };
  return (
    <section className="mainContiner">
      <div className="buttonContiner">
        <div className="QuestionLength">{questions.length}개의 질문</div>
        <Link to="/create_Question">
          <button className="ask_Question_Button">Ask Question</button>
        </Link>
      </div>
      <div className="filterButtonContiner">
        <button className="filterBtn" onClick={filterHandle}>
          Week
        </button>
        <button className="filterBtn">Month</button>
      </div>
      <div>
        {currentQuestions.map((el) => {
          return <Question info={el} key={el.id} />;
        })}
        {isLoading ? <div>Loading...</div> : <div ref={bottom}></div>}
      </div>
    </section>
  );
};

export default QuestionList;
