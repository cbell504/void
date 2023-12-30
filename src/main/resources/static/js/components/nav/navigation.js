import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import './navigation.css';
import Home from '../../void/home.jsx';
import Login from '../../void/login.jsx';

export default class Navigation extends Component {
  render() {
    return (
      <nav className="navigation">
        <div className="logo">
          <Link to="/">Your Logo</Link>
        </div>
        <ul className="nav-links">
          <li>
             <Link to="/login">Login</Link>
          </li>
        </ul>
        <div className="auth-buttons">
          <button className="login-button">Login</button>
          <button className="signup-button">Sign Up</button>
        </div>
      </nav>
    );
  }
}

window.addEventListener('load', () => {
  if (document.body.contains(document.getElementById('nav'))) {
    // Used to render the component on the page. The default refresh is when state is changed.
    ReactDOM.render(<Navigation />, document.getElementById('nav'));
  }
});