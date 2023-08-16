import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';
import ReactHtmlParser from 'react-html-parser';
import './QuestionDetail.css';
const QuestionDetail = () => {
  const { question_id } = useParams();
  const [data, setData] = useState(null);
  useEffect(() => {
    axios.get(`/question/${question_id}`).then((res) => setData(res.data));
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
          </div>
        </div>
      )}
    </div>
  );
};

export default QuestionDetail;
