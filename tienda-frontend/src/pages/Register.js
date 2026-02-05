import api from "../api/axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");
  const navigate = useNavigate();
	
  const handleRegister = async (e) => {
	  
	try {
      const res = await api.post("/users/register", { email, password, username });
	  alert(`User ${res.data.id} created`);

      navigate("/products"); // redirige a productos
    } catch (err) {
    console.error(err);
    alert("Register error.");
    }
	
  }

  return (
  <div>
    <form onSubmit={handleRegister}>
      <input type="email" placeholder="email" onChange={e => setEmail(e.target.value)} />
      <input type="password" placeholder="password" onChange={e => setPassword(e.target.value)} />
	  <input type="text" placeholder="username" onChange={e => setUsername(e.target.value)} />
      <button>Register</button>
    </form>
	
	<button 
	  onClick={() => navigate("/users/login")}
	  style={{ marginTop: "1rem", padding: "0.5rem 1rem" }}
    >
    Login
    </button>
  </div>
  );

}

export default Register;