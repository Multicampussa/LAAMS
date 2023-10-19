import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { legacy_createStore as createStore } from "redux";
import { Provider } from "react-redux";
import rootReducer from "./redux/reducer";

const root = ReactDOM.createRoot(document.getElementById("root"));
const store = createStore(rootReducer);

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);

reportWebVitals();