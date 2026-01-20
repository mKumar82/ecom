import { Link, useNavigate } from "react-router-dom";
import { useLoginMutation } from "../apiServices/authApi";
import { useState } from "react";
import { setToken } from "../redux/features/user/userSlice";
import { useAppDispatch } from "../redux/hooks";

const Login = () => {
  const [login] = useLoginMutation();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await login({ email, password }).unwrap();
      console.log("JWT response:", response.token);

      // dispatch to redux
      dispatch(setToken(response.token));
      navigate("/");
    } catch (err) {
      console.error("Login failed:", err);
    }
  };

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  console.log("Email:", email);
  console.log("Password:", password);

  return (
    <div className="bg-transparent w-full h-screen flex justify-center items-center">
      <div className="w-1/2 p-3 border rounded-2xl bg-white">
        <h1 className="text-3xl text-gray-900">Log in</h1>
        <form className="flex flex-col gap-4 mt-6">
          <input
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            type="email"
            placeholder="Email"
            className="border p-2 rounded-md"
          />
          <input
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            type="password"
            placeholder="Password"
            className="border p-2 rounded-md"
          />
          <button
            onClick={handleLogin}
            type="button"
            className="bg-blue-600 text-white p-2 rounded-md mt-4 hover:bg-blue-700"
          >
            Login
          </button>
        </form>
        <div className="flex justify-center">
          <Link to="/register" className="text-center p-2 rounded-md mt-4">
            or, sign up
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
