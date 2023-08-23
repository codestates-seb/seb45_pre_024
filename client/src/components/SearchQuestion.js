import axios from 'axios';
import { useEffect, useState, useRef, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import Question from './Question';
import PropTypes from 'prop-types';
import './QuestionList.css';
const SearchQuestion = ({ isLogin }) => {
  const { text } = useParams();
  const [searchList, setSearchList] = useState(null);
  const [totalSearch, setTotalSearch] = useState(null);
  const [page, setPage] = useState(1);
  const [maxPage, setMaxPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const bottom = useRef(null);
  useEffect(() => {
    axios
      .get(
        `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question/find?page=1&size=10&query=${text}`,
      )
      .then((res) => {
        setSearchList(res.data.data);
        console.log(res.data);
        setMaxPage(res.data.pageInfo.totalPages);
        setTotalSearch(res.data.pageInfo.totalElements);
        setPage(page + 1);
        setIsLoading(false);
      });
  }, [text]);
  const renderNextPage = useCallback(() => {
    if (page <= maxPage) {
      setIsLoading(true);
      axios
        .get(
          `http://ec2-3-39-152-190.ap-northeast-2.compute.amazonaws.com:8080/question/find?page=${page}&size=10&query=${text}`,
        )
        .then((res) => {
          setSearchList(searchList.concat(res.data.data));
          setPage(page + 1);
          setIsLoading(false);
        });
    }
  }, [page, searchList]);
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
  return (
    <section className="mainContiner">
      <div className="mainContent">
        <div className="line1">
          <h1 className="allQuestions">Search Results</h1>
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
          <div className="questionCount">{totalSearch} questions</div>
          <div className="filterContainer">
            <button className="filterBtn1">Interesting</button>
            <button className="filterBtn2">Bountied</button>
            <button className="filterBtn3">Hot</button>
            <button className="filterBtn4">Week</button>
            <button className="filterBtn5">Month</button>
          </div>
        </div>
        <div className="qtContainer">
          <div className="qtLiContainer">
            <div className="qtLiDiv">
              {searchList &&
                searchList.map((el) => {
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

SearchQuestion.propTypes = {
  isLogin: PropTypes.bool,
};
export default SearchQuestion;
