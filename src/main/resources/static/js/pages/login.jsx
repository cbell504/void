import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import LoginForm from '../components/forms/loginform.js'

class Login extends Component {
  render() {
    return (
      <div>
        <LoginForm />
      </div>
    );
  }
}

export default Login;