import api from "../api/axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Products() {
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/products")
      .then(res => setProducts(res.data))
      .catch(err => alert("Error loading products"));
  }, []);

  // Añadir producto al carrito (adaptado a tu endpoint)
  const addToCart = async (productId) => {
  try {
    await api.post(`/cart/add/${productId}`);
    alert("Product added to cart");
  } catch (err) {
    console.log(err.response);
    alert("Error adding product to cart");
  }
};

  return (
    <>
      <ul>
        {products.map(p => (
          <li key={p.id}>

            {p.name} - {p.price}€
            <button 
			  onClick={() => addToCart(p.id)}
			  style={{ marginRight: "1rem", padding: "0.5rem 1rem" }}
			>

              Añadir al carrito
            </button>
          </li>
        ))}
      </ul>

      <button onClick={() => navigate("/cart")}>
        Go checkout
      </button>
    </>
  );
}

export default Products;