import { useState } from "react";
import { useRegisterMutation } from "../apiServices/authApi";
import { useAppDispatch } from "../redux/hooks";
import { useNavigate } from "react-router-dom";
import { setToken } from "../redux/features/user/userSlice";

const Register = () => {
  const [register] = useRegisterMutation();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const response = await register({ name, email, password }).unwrap();
      console.log("JWT response:", response);

      // dispatch to redux
      dispatch(setToken(response.token));
      localStorage.setItem("token", response.token);
      navigate("/");
    } catch (err) {
      console.error("register failed:", err);
    }
  };
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  console.log("username:", setName);
  console.log("Email:", email);
  console.log("Password:", password);
  return (
    <div className="bg-transparent w-full h-screen flex justify-center items-center">
      <div className="w-1/2 p-3 border rounded-2xl bg-white">
        <h1 className="text-3xl text-gray-900">Sign up</h1>
        <form className="flex flex-col gap-4 mt-6">
          <input
            value={name}
            onChange={(e) => setName(e.target.value)}
            type="text"
            placeholder="Username"
            className="border p-2 rounded-md"
          />
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
            onClick={handleRegister}
            type="button"
            className="bg-blue-600 text-white p-2 rounded-md mt-4 hover:bg-blue-700"
          >
            Sign up
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
