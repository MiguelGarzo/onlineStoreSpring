import api from "../api/axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
  e.preventDefault();
  try {
    const res = await api.post("/users/login", { email, password });

    // 🔹 res.data ya es el token directamente
    const token = res.data;
    if (!token) {
      alert("Login faliled: backend doesnot return token");
      return;
    }

    localStorage.setItem("token", token); // ✅ guardamos JWT real
    alert("Login correct");

    navigate("/products"); // redirige a productos
  } catch (err) {
    console.error(err);
    alert("Error login");
  }
};

  return (
  <div>
		<form onSubmit={handleLogin}>
		  <input placeholder="email" onChange={e => setEmail(e.target.value)} />
		  <input type="password" placeholder="password" onChange={e => setPassword(e.target.value)} />
		  <button>Login</button>
		</form>
		
		<button 
		  onClick={() => navigate("/users/register")}
		  style={{ marginTop: "1rem", padding: "0.5rem 1rem" }}
		>
		Register
		</button>
	</div>
  );
}

export default Login;