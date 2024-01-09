import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Navigate } from 'react-router-dom';
import './crygallery.css';

export default class CryGallery extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isAccountLoggedIn: false,
      notLoggedInMessage: 'You are not logged in.'
      loginToken: localStorage.getItem('loginToken')
    };
  }

  getCries = async (event) => {
    event.preventDefault();
    const { email, password } = this.state;
    const clientId = 'void-client';
    const url = '/api/accounts/v1/login';
    const account = {"accountSecurity":{"email":email, "password":password}}
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'clientId': clientId
      },
      body: JSON.stringify({account})
    };
    fetch(url, options)
      .then(response => {
        if (response.status === 200) {
          const loginToken = response.headers.get('loginToken');
          localStorage.setItem('loginToken', loginToken);
          window.location.href = '/';
        } else {
          this.setState({
            showPopup: true,
            popupMessage: 'The login information given is incorrect.'
          });
        }
      })
      .catch(error => console.log(error));
  };

  handleChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  }

  render() {
    const { isAccountLoggedIn, notLoggedInMessage } = this.state;
    return (
      <div className="cry-gallery">
        {isAccountLoggedIn === true && (
          <div className="cry-entry">
            <div className="cry-author"></div>
            <div className="cry-text"><div>
          </div>
        )}
        {isAccountLoggedIn === false && (
          <p>{notLoggedInMessage}</p>
        )}
      </div>
    );
  }
}

window.addEventListener('load', () => {
  if (document.body.contains(document.getElementById('cryGallery'))) {
    // Used to render the component on the page. The default refresh is when state is changed.
    ReactDOM.render(<CryGallery />, document.getElementById('cryGallery'));
  }
});
