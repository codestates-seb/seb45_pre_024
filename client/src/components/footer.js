import './footer.css';
function Footer() {
  const envURL = process.env.PUBLIC_URL;
  return (
    <div>
      <div className="footer_container">
        <nav className="footer_nav">
          <div className="logo_container">
            <img className="logo" src={envURL + '/logo.png'} alt="logo"></img>
          </div>
          <div className="stack_overflow">
            <h5>STACK OVERFLOW</h5>
            <li>Questions</li>
            <li>Help</li>
          </div>
          <div className="team_notion">
            <h5>TEAM</h5>
            <li>
              <a
                href="https://www.notion.so/codestates/bed61b7145874133b9fa28a29a7156aa?pvs=4"
                target="_blank"
                rel="noreferrer"
              >
                이삿날
              </a>
            </li>
          </div>
          <div className="member_fe">
            <h5>MEMBER</h5>
            <li>Frontend Developer</li>
            <li>
              <a
                href="https://github.com/ysm990926"
                target="_blank"
                rel="noreferrer"
              >
                윤선문
              </a>
            </li>
            <li>
              <a
                href="https://github.com/yejicho-helloworld"
                target="_blank"
                rel="noreferrer"
              >
                조예지
              </a>
            </li>
            <li>
              <a
                href="https://github.com/SonSeolHui"
                target="_blank"
                rel="noreferrer"
              >
                손설희
              </a>
            </li>
          </div>
          <div className="member_be">
            <h5>&nbsp;</h5>
            <li>Backend Developer</li>
            <li>
              <a
                href="https://github.com/jjo3ys"
                target="_blank"
                rel="noreferrer"
              >
                조연성
              </a>
            </li>
            <li>
              <a
                href="https://github.com/kangsuck"
                target="_blank"
                rel="noreferrer"
              >
                이강석
              </a>
            </li>
            <li>
              <a
                href="https://github.com/minprr1029"
                target="_blank"
                rel="noreferrer"
              >
                윤민지
              </a>
            </li>
          </div>
          <div className="info">
            <p>
              Site design / logo © 2023 Stack Exchange Inc; user
              <br />
              contributions licensed under CC BY-SA.
              <br />
              rev 2023.8.9.43572
            </p>
          </div>
        </nav>
      </div>
    </div>
  );
}
export default Footer;
