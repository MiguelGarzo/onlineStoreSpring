import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";

const stripePromise = loadStripe("pk_test_51Sw1pE5uGekwXb2yyVkbqOzJhgq3acgtn91Ofhc1w94CB0Qzv48YfwLiuDbWM00rNXhaKcbuEsT7VQKR2wEArNZT00fAMFFNLO");

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <Elements stripe={stripePromise}>
      <App />
    </Elements>
  </React.StrictMode>
);


