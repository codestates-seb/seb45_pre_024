import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState, useCallback, useRef } from 'react';
import ReactHtmlParser from 'react-html-parser';
import Wepediter from './Wepediter';
import './QuestionDetail.css';
import Answer from './Answer';
import PropTypes from 'prop-types';
const QuestionDetail = ({ isLogin }) => {
  const { question_id } = useParams();
  const [data, setData] = useState(null);
  const [answer, setAnswer] = useState(null);
  const [page, setPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [maxPage, setMaxPage] = useState(null);
  const bottom = useRef(null);
  useEffect(() => {
    axios.get(`/question/${question_id}`).then((res) => setData(res.data));
    FetchAnswerData();
  }, []);
  const FetchAnswerData = () => {
    axios.get(`/answer/${question_id}?page=1&size=10`).then((res) => {
      setAnswer(res.data.data);
      setPage(page + 1);
      setIsLoading(false);
      setMaxPage(res.data.pageInfo.totalPages);
    });
  };

  const renderNextPage = useCallback(() => {
    if (page <= maxPage) {
      setIsLoading(true);
      axios.get(`/answer/${question_id}?page=${page}&size=10`).then((res) => {
        setAnswer(answer.concat(res.data.data));
        setPage(page + 1);
        setIsLoading(false);
      });
    }
  }, [page, answer]);

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
    <div className="mainContainer">
      {data && (
        <div>
          <div className="title">
            <span>타이틀 : {data.title}</span>
            <span>작성자 : {data.username}</span>
          </div>
          <div className="detailContainer">
            <span> 조회수 : {data.view_count}</span>
            <span>
              최근수정:
              {data.modified_at.slice(0, 10) +
                ' ' +
                data.modified_at.slice(11, 19)}{' '}
              작성일자:
              {data.created_at.slice(0, 10) +
                ' ' +
                data.created_at.slice(11, 19)}
            </span>
          </div>
          <div className="bodyContainer">
            <div className="body">
              <div>질문내용 {ReactHtmlParser(data.body)}</div>
            </div>
          </div>
          <div className="createAnswer">
            <Wepediter
              type="Answer"
              question_id={question_id}
              isLogin={isLogin}
              FetchAnswerData={FetchAnswerData}
            />
          </div>
          <div>
            {answer &&
              answer.map((el) => {
                return <Answer info={el} key={el.answer_id} />;
              })}
            {isLoading ? <div>Loading...</div> : <div ref={bottom}></div>}
          </div>
        </div>
      )}
    </div>
  );
};

QuestionDetail.propTypes = {
  isLogin: PropTypes.bool,
};
export default QuestionDetail;
