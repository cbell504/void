import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import CryGallery from '../components/cries/crygallery.js'
import CryPoster from '../components/forms/cryposter.js'

class HomePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isAccountLoggedIn: localStorage.hasOwnProperty('voidLoginToken') && localStorage.hasOwnProperty("voidAccountId")
        };
    }

    render() {
        return (
            <div id="home-page-root">
                <CryPoster />
                <CryGallery />
            </div>
        );
    }
}

export default HomePage;