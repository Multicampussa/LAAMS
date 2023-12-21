import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { legacy_createStore as createStore } from "redux";
import { Provider } from "react-redux";
import rootReducer from "./redux/reducer";

const root = ReactDOM.createRoot(document.getElementById("root"));
const store = createStore(rootReducer);

console.log = function no_console() {};
console.warn = function no_console() {}; 
console.error = function () {}; 

root.render(
  <Provider store={store}>
    <App />
  </Provider>
);

reportWebVitals();