import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Outlet, Link } from 'react-router-dom';
import Navigation from "../components/nav/navigation.js"

class Home extends Component {
  render() {
    return (
        <div>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/dashboard">Dashboard</Link>
              </li>
              <li>
                <Link to="/nothing-here">Nothing Here</Link>
              </li>
            </ul>
          </nav>
          <Navigation />
          <Outlet />
        </div>
      );
  }
}

export default Home;