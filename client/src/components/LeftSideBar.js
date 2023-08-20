// import React from 'react';
import './LeftSidebar.css';
import { NavLink } from 'react-router-dom';

const LeftSidebar = () => {
  return (
    <div className="left-sidebar">
      <nav className="side-nav">
        <NavLink to="/" className="side-nav-links" activeclassname="active">
          <p className="font">Home</p>
        </NavLink>
        <div className="side-nav-div">
          <div>
            <p className="font">PUBLIC</p>
          </div>
          <NavLink
            to="/Questions"
            className="side-nav-links"
            activeclassname="active"
          >
            {/* <img src={Globe} alt="Globe" /> */}
            <p style={{ paddingLeft: '10px' }} className="font">
              {' '}
              Questions{' '}
            </p>
          </NavLink>
          <NavLink
            to="/Tags"
            className="side-nav-links"
            activeclassname="active"
            style={{ paddingLeft: '40px' }}
          >
            <p className="font">Tags</p>
          </NavLink>
          <NavLink
            to="/Users"
            className="side-nav-links"
            activeclassname="active"
            style={{ paddingLeft: '40px' }}
          >
            <p className="font">Users</p>
          </NavLink>
        </div>
      </nav>
    </div>
  );
};

export default LeftSidebar;
