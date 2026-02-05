import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";
import { useStripe, useElements, CardElement } from "@stripe/react-stripe-js";

function Cart() {
  const [cart, setCart] = useState({ items: [], total: 0 });
  const [orders, setOrders] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const stripe = useStripe();
  const elements = useElements();
  const navigate = useNavigate();

  // Cargar carrito
  const loadCart = async () => {
    try {
      const res = await api.get("/cart");
      setCart(res.data);
    } catch (err) {
      console.error(err);
      alert("Error loading the cart");
    }
  };

  useEffect(() => {
    loadCart();
	loadOrders();
  }, []);

  // Crear pedido
  const createOrder = async () => {
    try {
      const res = await api.post("/orders");
      alert(`Order ${res.data.id} created`);
      loadCart(); // recargar carrito vacío
    } catch (err) {
      console.error(err);
      alert("Error creating order");
    }
  };

  // Ver mis pedidos
  const loadOrders = async () => {
	  try {
		const res = await api.get("/orders/my");
		setOrders(res.data); // aquí asumimos que el backend devuelve un array de orders
	  } catch (err) {
		console.error("Error loading orders:", err);
		alert("Cant load the orders");
	  }
	};

  // Pagar pedido
  const payOrder = async (orderId) => {
  if (!stripe || !elements) return;

  try {
    // 1️ Crear PaymentIntent en backend
    const res = await api.post(`/payments/stripe/pay/${orderId}`);
    const clientSecret = res.data.clientSecret;

    // 2️ Confirmar pago con Stripe
    const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
      payment_method: {
        card: elements.getElement(CardElement)
      }
    });

    if (error) {
      alert(error.message);
    } else if (paymentIntent.status === "succeeded") {
      alert(`Order ${orderId} paid`);
      loadOrders(); // refresca lista de pedidos
    }
  } catch (err) {
    console.error(err);
    alert("Error with the payment");
  }
};

  return (
    <div style={{ maxWidth: "800px", margin: "auto", padding: "2rem" }}>
  <h2>My cart</h2>

  {cart.items.length > 0 ? (
    <>
      <ul>
        {cart.items.map(item => (
          <li key={item.productId}>
            {item.productName} - {item.price}€ x {item.quantity} = {item.subtotal}€
          </li>
        ))}
      </ul>

      <h3>Total: {cart.total}€</h3>

      <button
        onClick={createOrder}
        style={{ marginRight: "1rem", padding: "0.5rem 1rem" }}
      >
        Make an order
      </button>
    </>
  ) : (
    <p>Empty cart</p>
  )}
  
  <button 
	onClick={() => navigate("/products")}
	style={{ marginRight: "1rem", padding: "0.5rem 1rem" }}
  >
    Back to products
  </button>
   
  <hr style={{ margin: "2rem 0" }} />

  <h2>My orders</h2>
  <ul>
    {orders.map(order => (
      <li key={order.id}>
        <strong>Order #{order.id}</strong> - Total: {order.total}€ - Status: {order.status}
        <ul>
          {order.items.map((item, idx) => (
            <li key={idx}>
              {item.productName} - {item.price}€ x {item.quantity} = {item.subtotal}€
            </li>
          ))}
        </ul>

        {/* Botón para pagar solo si no está pagado y no es el pedido seleccionado */}
        {order.status !== "PAID" && selectedOrder?.id !== order.id && (
          <button onClick={() => setSelectedOrder(order)}>
            Pagar pedido
          </button>
        )}
      </li>
    ))}
  </ul>

  {/* Área de pago solo para el pedido seleccionado */}
  {selectedOrder && (
    <div style={{ marginTop: "1rem", border: "1px solid #ccc", padding: "1rem" }}>
      <h3>Pagar pedido #{selectedOrder.id}</h3>
      <CardElement />
      <button
        style={{ marginRight: "0.5rem" }}
        onClick={() => payOrder(selectedOrder.id)}
      >
        Pagar
      </button>
      <button onClick={() => setSelectedOrder(null)}>Cancelar</button>
    </div>
  )}
</div>
  );
}

export default Cart;