import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';
import ReactHtmlParser from 'react-html-parser';
import Wepediter from './Wepediter';
import './QuestionDetail.css';
const QuestionDetail = () => {
  const { question_id } = useParams();
  const [data, setData] = useState(null);
  const [answer, setAnswer] = useState(null);
  useEffect(() => {
    axios.get(`/question/${question_id}`).then((res) => setData(res.data));
    axios.get(`/answer/${question_id}`).then((res) => {
      if (res.status === 200) {
        setAnswer(res.data);
      }
    });
  }, []);
  return (
    <div className="mainContainer">
      {data && (
        <div>
          <div className="title">{data.title}</div>
          <div className="bodyContainer">
            <div className="body">
              <div>{ReactHtmlParser(data.body)}</div>
            </div>
            <div className="createAnswer">
              <Wepediter type="Answer" question_id={question_id} />
            </div>
            {answer && <div className="answer">{answer.length}</div>}
          </div>
        </div>
      )}
    </div>
  );
};

export default QuestionDetail;
