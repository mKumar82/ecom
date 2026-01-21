import { Link, useNavigate } from "react-router-dom";
import { useLoginMutation } from "../apiServices/authApi";
import { useState } from "react";
import { setToken } from "../redux/features/user/userSlice";
import { useAppDispatch } from "../redux/hooks";
import toast from "react-hot-toast";

const Login = () => {
  const [login, { isLoading }] = useLoginMutation();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleLogin = async (e:any) => {
    e.preventDefault(); 
    try {
      const response = await login({ email, password }).unwrap();
      console.log("JWT response:", response.token);

      // dispatch to redux
      dispatch(setToken(response.token));
      toast.success("Logged in successfully");
      navigate("/");
    } catch (err) {
      console.error("Login failed:", err);
      toast.error("Invalid email or password");
    }
  };

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const isEmpty: boolean = !email || !password;
  console.log("-------------", isEmpty);

  console.log("Email:", email);
  console.log("Password:", password);

  return (
    <div className="bg-transparent w-full h-screen flex justify-center items-center">
      <div className="w-full md:w-1/2 lg:w-1/4 p-3 m-5 border rounded-2xl bg-white">
        <h1 className="text-3xl text-gray-900">Log in</h1>
        <form onSubmit={handleLogin} className="flex flex-col gap-4 mt-6">
          <input
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            type="email"
            placeholder="Email"
            className="border p-2 rounded-md"
            required={true}
          />
          <input
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            type="password"
            placeholder="Password"
            className="border p-2 rounded-md"
            required={true}
          />
          <button
            disabled={isLoading || isEmpty}
            // onClick={handleLogin}
            type="submit"
            // className="bg-blue-600 text-white p-2 rounded-md mt-4 hover:bg-blue-700"
            className={`px-4 py-2 rounded ${
              isLoading || isEmpty ? "bg-gray-400" : "bg-yellow-400"
            }`}
          >
            {isLoading ? "Loggin..." : "Login"}
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
