import { useState } from "react";
import { Link } from "react-router-dom";
import { loginUser } from "../api/authService";
import { useNavigate } from "react-router-dom";


function Login() {

    const [form, setForm] = useState({

        email: "",

        password: ""

    });

    const handleChange = (e) => {

        setForm({

            ...form,

            [e.target.name]: e.target.value

        });

    };

    const navigate = useNavigate();

const handleSubmit = async (e) => {

    e.preventDefault();

    try {

       const response = await loginUser(form);

localStorage.setItem("token", response.data.token);

navigate("/dashboard");

    } catch (error) {

        alert(error.response?.data || "Login Failed");

    }

};

    return (

        <div className="container">

            <div className="card">

                <h2>Login</h2>

                <form onSubmit={handleSubmit}>

                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        onChange={handleChange}
                    />

                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        onChange={handleChange}
                    />

                    <button type="submit">
                        Login
                    </button>

                </form>

                <p>

                    Don't have an account?

                    <Link to="/register"> Register</Link>

                </p>

            </div>

        </div>

    );

}

export default Login;