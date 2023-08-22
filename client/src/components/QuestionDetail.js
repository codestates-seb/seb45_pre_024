import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState, useCallback, useRef } from 'react';
import ReactHtmlParser from 'react-html-parser';
import Wepediter from './Wepediter';
import './QuestionDetail.css';
import Answer from './Answer';
import PropTypes from 'prop-types';
import CommentForm from './QuestionComment';

const QuestionDetail = ({ isLogin }) => {
  const { question_id } = useParams();
  const [data, setData] = useState(null);
  const [answer, setAnswer] = useState(null);
  const [page, setPage] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [maxPage, setMaxPage] = useState(null);
  const bottom = useRef(null);
  const [isDelete, setIsDelete] = useState(false);
  const [isFix, setIsFix] = useState(false);
  const [textContent, setTextContent] = useState('');
  const [isError, setIsError] = useState(false);
  const [errText, setErrText] = useState('');
  const navi = useNavigate();
  useEffect(() => {
    axios.get(`/question/${question_id}`).then((res) => {
      setData(res.data);
      setTextContent(res.data.title);
    });
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
  const renderCurrentPage = () => {
    setAnswer(null);
    setIsLoading(true);
    for (let i = 1; i <= page; i++) {
      axios.get(`/answer/${question_id}?page=${i}&size=10`).then((res) => {
        setAnswer(answer.concat(res.data.data));
        console.log(answer);
      });
    }
    setIsLoading(false);
  };
  const acceptHandle = (answer_id) => {
    const header = {
      headers: {
        Authorization: sessionStorage.getItem('authorization'),
        Refresh: sessionStorage.getItem('refresh'),
      },
    };
    axios.patch(`/answer/accept/${answer_id}`, null, header);
    renderCurrentPage();
  };
  const deleteQuestion = () => {
    if (!isDelete) {
      setIsDelete(true);
    } else {
      const header = {
        headers: {
          Authorization: sessionStorage.getItem('authorization'),
          Refresh: sessionStorage.getItem('refresh'),
        },
      };
      axios
        .delete(`/question/${question_id}`, header)
        .then((res) => {
          console.log(res);
          if (res.status === 204) navi('/');
        })
        .catch(console.error('err'));
    }
  };
  const textHandle = (e) => {
    setTextContent(e.target.value);
  };
  const errhandle = (e) => {
    setIsError(true);
    setErrText(e);
  };
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
            <span className="title">{data.title}</span>
            <span className="username">Username: {data.username}</span>
          </div>
          <div className="detailContainer">
            <span className="view_count"> Viewed: {data.view_count}</span>
            <span className="detail_modified">
              Modified:
              {data.modified_at.slice(0, 10) +
                ' ' +
                data.modified_at.slice(11, 19)}{' '}
            </span>
            <span className="detail_asked">
              Asked:
              {data.created_at.slice(0, 10) +
                ' ' +
                data.created_at.slice(11, 19)}
            </span>
            {data.username === sessionStorage.getItem('username') && (
              <span>
                <button onClick={() => setIsFix(!isFix)}>수정하기</button>
                <button onClick={deleteQuestion}>삭제하기</button>
                {isDelete && (
                  <span className="reqText">정말 삭제하시겠습니까?</span>
                )}
              </span>
            )}
          </div>
          {isFix ? (
            <div>
              <input
                type="text"
                placeholder="Enter Fix Question title"
                value={textContent}
                onChange={(e) => {
                  textHandle(e);
                }}
              ></input>
              <Wepediter
                type={'FixQuestion'}
                init={data.body}
                title={textContent}
                isLogin={isLogin}
                question_id={question_id}
                errhandle={errhandle}
              />
              {isError && <div className="errText">{errText}</div>}
            </div>
          ) : (
            <div className="bodyContainer">
              <div className="body">
                <div>질문내용 {ReactHtmlParser(data.body)}</div>
              </div>
            </div>
          )}
          <div className="your_answer">Your Answer</div>
          <div className="createAnswer">
            <Wepediter
              type="Answer"
              question_id={question_id}
              isLogin={isLogin}
              renderCurrentPage={renderCurrentPage}
            />
          </div>
          <div className="comment-section">
            {' '}
            <h3>댓글 작성</h3>
            <CommentForm
              question_id={question_id}
              renderCurrentPage={renderCurrentPage}
            />
          </div>
          {data &&
            data.question_comment &&
            data.question_comment.length &&
            data.question_comment.map((el, index) => {
              return <div key={index}>{el.body}</div>;
            })}
          <div>
            {answer &&
              answer.map((el) => {
                return (
                  <div key={el.answer_id}>
                    <Answer info={el} />
                    {data.username === sessionStorage.getItem('username') && (
                      <button onClick={() => acceptHandle(el.answer_id)}>
                        {el.accepted ? '채택취소하기' : '채택하기'}
                      </button>
                    )}
                  </div>
                );
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
  user: PropTypes.string,
};
export default QuestionDetail;
