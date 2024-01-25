import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import CryGallery from '../components/cries/crygallery.js'

class Home extends Component {
  render() {
    return (
        <div>
          <p>This is the home page.</p>
          <CryGallery />
        </div>
    );
  }
}

export default Home;