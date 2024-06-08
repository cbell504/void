import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import './navigation.css';
import Search from '../forms/search.js'

export default class Navigation extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isAccountLoggedIn: localStorage.hasOwnProperty('voidLoginToken') && localStorage.hasOwnProperty("voidAccountId"),
      notLoggedInMessage: 'You are not logged in.',
      responseData: null,
      loading: true,
    };
  }

  logout = (e) => {
    e.preventDefault();
    console.log("Logging out")
    localStorage.removeItem('voidLoginToken');
    localStorage.removeItem('voidAccountId');
    window.location.href = '/';
  }

  render() {
    const { isAccountLoggedIn } = this.state;
    return (
      <nav className="navigation">
        <div className="logo">
          <Link to="/">The Void</Link>
        </div>
        <Search />
        {isAccountLoggedIn === false && (
          <div className="auth-buttons">
            <button className="login-button"><Link to="/login">Login</Link></button>
            <button className="signup-button"><Link to="/signup">Sign Up</Link></button>
          </div>
        )}
        {isAccountLoggedIn && (
          <div className="auth-buttons">
            <button className="profile-button"> <Link to="/profile">Profile</Link></button>
            <button className="logout-button" onClick={this.logout}> <Link to="/">Logout</Link></button>
          </div>
        )}
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