// import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import logo from '../assets/logo.png';
import search from '../assets/magnify.svg';
// import Button from './Button';
import Avatar from './Avatar';
import './Header.css';
import { useState } from 'react';

const Header = ({ isLogin, logout }) => {
  const [text, setText] = useState('');
  const handleSubmit = (e) => {
    e.preventDefault;
  };
  return (
    <nav className="nav">
      <div className="navbar">
        <Link to="/" className="nav-item nav-logo">
          <img src={logo} alt="logo" width="200px" />
        </Link>
        <Link to="/" className="nav-item nav-btn">
          About
        </Link>
        <Link to="/" className="nav-item nav-btn">
          Products
        </Link>
        <Link to="/" className="nav-item nav-btn">
          For teams
        </Link>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="       Search..."
            value={text}
            onChange={(e) => setText(e.target.value)}
            className="search-input"
          />
          <img src={search} alt="search" width="18" className="search-icon" />
        </form>
        {!isLogin ? (
          <>
            <Avatar
              backgroundColor="transparent"
              px="10px"
              py="10px"
              borderRadius="50%"
              color="white"
            >
              <Link
                to="/User"
                style={{ color: 'white', textDecoration: 'none' }}
              >
                Y
              </Link>
            </Avatar>
            <Link to="/signin" className="login">
              <button className="login">Log in</button>
            </Link>
          </>
        ) : (
          <>
            <Avatar
              backgroundColor="#009dff"
              px="10px"
              py="10px"
              borderRadius="50%"
              color="white"
            >
              <Link
                to="/User"
                style={{ color: 'white', textDecoration: 'none' }}
              >
                Y
              </Link>
            </Avatar>
            <Link to="/">
              <button className="nav-item nav-links" onClick={logout}>
                Log out
              </button>
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

Header.propTypes = {
  isLogin: PropTypes.bool,
  logout: PropTypes.func,
};
export default Header;
