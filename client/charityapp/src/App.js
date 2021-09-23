import React from 'react';
import { useState } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import API, { AuthAPI, endpoints } from './API';
import Home from './pages/Home';
import LoginForm from './pages/LoginForm';
import SignUpForm from './pages/SignUpForm';
import Profile from './components/Profile';

export let UserContext = React.createContext()

export default function App(props) {
  
  return (
      <BrowserRouter>
        <Switch>
          <Route  exact path="/" component={LoginForm}/>
          <Route exact path="/sign-up" component={SignUpForm}/>
          <Route exact path="/home" component={Home}/>
          <Route exact path="/my-profile" component={Profile}/>

        </Switch>
      </BrowserRouter> 
  );
}

