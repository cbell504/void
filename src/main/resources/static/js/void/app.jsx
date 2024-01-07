import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Routes, Route, Link } from "react-router-dom";
import Home from './home.jsx';
import Login from './login.jsx';
import Layout from './layout.jsx';
import { NoMatch } from "./no-match.jsx";
import SignUp from './signup.jsx';

class App extends Component {
  render() {
    return (
    <div>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<SignUp />} />
          <Route path="*" element={<NoMatch />} />
        </Route>
      </Routes>
      </div>
    );
  }
}

export default App;