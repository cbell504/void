import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import ProfileCryGallery from '../components/cries/profilecrygallery.js'

class ProfilePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isAccountLoggedIn: localStorage.hasOwnProperty('voidLoginToken') && localStorage.hasOwnProperty("voidAccountId")
        };
    }

    render() {
        return (
            <div id="profile-page-root">
                <ProfileCryGallery />
            </div>
        );
    }
}

export default ProfilePage;