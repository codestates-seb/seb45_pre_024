// import React from 'react';
import './LeftSidebar.css';
import { NavLink } from 'react-router-dom';

const LeftSidebar = () => {
  return (
    <div className="left-sidebar">
      <nav className="side-nav">
        <NavLink to="/" className="side-nav-links" activeclassname="active">
          <p className="sbHome">Home</p>
        </NavLink>
        <div className="side-nav-div">
          <div>
            <p className="sbPublic">PUBLIC</p>
          </div>
          <NavLink
            to="/Questions"
            className="side-nav-links"
            activeclassname="active"
          >
            {/* <img src={Globe} alt="Globe" /> */}
            <p style={{ paddingLeft: '10px' }} className="sbQu">
              Questions
            </p>
          </NavLink>
          <NavLink
            to="/Tags"
            className="side-nav-links"
            activeclassname="active"
            style={{ paddingLeft: '40px' }}
          >
            <p className="sbTags">Tags</p>
          </NavLink>
          <NavLink
            to="/Users"
            className="side-nav-links"
            activeclassname="active"
            style={{ paddingLeft: '40px' }}
          >
            <p className="sbUsers">Users</p>
          </NavLink>
        </div>
      </nav>
    </div>
  );
};

export default LeftSidebar;
