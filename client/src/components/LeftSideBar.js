// import React from 'react';
import './LeftSidebar.css';
import { NavLink } from 'react-router-dom';

const LeftSidebar = () => {
  return (
    <div className="left-sidebar">
      <nav className="side-nav">
        <ul className="sbLinks">
          <NavLink to="/" className="sbHomeDiv" activeclassname="active">
            <li className="sbHome">Home</li>
          </NavLink>
          <li>
            <ol className="side-nav-div">
              <div>
                <li className="sbPublic">PUBLIC</li>
              </div>
              <NavLink
                to="/Questions"
                className="side-nav-links"
                activeclassname="active"
              >
                {/* <img src={Globe} alt="Globe" /> */}
                <li className="sbQu">Questions</li>
              </NavLink>
              <NavLink
                to="/Tags"
                className="side-nav-links"
                activeclassname="active"
              >
                <li className="sbTags">Tags</li>
              </NavLink>
              <NavLink
                to="/Users"
                className="side-nav-links"
                activeclassname="active"
              >
                <li className="sbUsers">Users</li>
              </NavLink>
            </ol>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default LeftSidebar;
