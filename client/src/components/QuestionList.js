import { useState, useEffect } from 'react';
import axios from 'axios';
import Question from './Question';
import './QuestionList.css';
import { Link } from 'react-router-dom';
const QuestionList = () => {
  const [questions, setQuestions] = useState([]);
  const [filter, setFilter] = useState('');
  useEffect(() => {
    axios
      .get(`http://localhost:3001/Question${filter}`)
      .then((res) => {
        setQuestions(res.data.data);
      })
      .catch(console.log('err'));
  }, []);
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
        {questions.map((el) => {
          return <Question info={el} key={el.id} />;
        })}
      </div>
    </section>
  );
};

export default QuestionList;
