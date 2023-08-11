// import React from 'react';
import PropTypes from 'prop-types';

const Avatar = ({
  children,
  backgroundColor,
  px,
  py,
  color,
  borderRadius,
  fontSize,
  cursor,
}) => {
  const style = {
    backgroundColor,
    padding: `${py} ${px}`,
    color: color || 'black',
    borderRadius,
    fontSize,
    textAlign: 'center',
    cursor: cursor || null,
    textDecoration: 'none',
  };
  return <div style={style}>{children}</div>;
};

Avatar.propTypes = {
  children: PropTypes.string,
  backgroundColor: PropTypes.string,
  px: PropTypes.string,
  py: PropTypes.string,
  color: PropTypes.string,
  borderRadius: PropTypes.string,
  fontSize: PropTypes.string,
  cursor: PropTypes.string,
};
export default Avatar;
