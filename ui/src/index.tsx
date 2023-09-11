import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import {  UserdetailsProvider } from './components/UserContext';
import axios from "axios"

axios.defaults.baseURL = 'http://172.16.16.119:8080'
axios.defaults.headers.post["Content-Type"] = 'application/json'
axios.defaults.headers.post["Accept"] = '*/*'
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*"
axios.defaults.headers.post["Access-Control-Allow-Headers"] = "*"
const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    
    <UserdetailsProvider>
    <BrowserRouter>
    <App/>
    </BrowserRouter>
    </UserdetailsProvider>
  
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
