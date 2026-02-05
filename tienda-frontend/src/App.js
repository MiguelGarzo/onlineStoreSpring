import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Products from "./pages/ProductList";
import Cart from "./pages/Cart";
import Register from "./pages/Register";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/users/login" element={<Login />} />
        <Route path="/products" element={<Products />} />
		<Route path="/cart" element={<Cart />} />
		<Route path="/users/register" element={<Register />} />
        <Route path="*" element={<div>Página no encontrada</div>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
