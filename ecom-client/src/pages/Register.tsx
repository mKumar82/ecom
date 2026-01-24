import { useState } from "react";
import { useRegisterMutation } from "../apiServices/authApi";
import { useAppDispatch } from "../redux/hooks";
import { useNavigate } from "react-router-dom";
import { setToken } from "../redux/features/user/userSlice";
import toast from "react-hot-toast";

const Register = () => {
  const [register, { isLoading }] = useRegisterMutation();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleRegister = async (e: any) => {
    e.preventDefault();
    try {
      const response = await register({ name, email, password }).unwrap();
      console.log("JWT response:", response);

      // dispatch to redux
      dispatch(setToken(response.token));
      localStorage.setItem("token", response.token);
      toast.success("Account created successfully");
      navigate("/");
    } catch (err) {
      console.error("register failed:", err);
    }
  };
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const isEmpty: boolean = !name || !email || !password;

  console.log("username:", setName);
  console.log("Email:", email);
  console.log("Password:", password);
  return (
    <div className="bg-transparent w-full h-screen flex justify-center items-center">
      <div className="w-full md:w-1/2 lg:w-1/4 p-3 m-5 border rounded-2xl bg-white">
        <h1 className="text-3xl text-gray-900">Sign up</h1>
        <form onSubmit={handleRegister} className="flex flex-col gap-4 mt-6">
          <input
            value={name}
            onChange={(e) => setName(e.target.value)}
            type="text"
            placeholder="Username"
            className="border p-2 rounded-md"
            required={true}
          />
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
            // onClick={handleRegister}
            type="submit"
            className={`px-4 py-2 rounded ${
              isLoading || isEmpty ? "bg-gray-400" : "bg-yellow-400"
            }`}
          >
            {isLoading ? "Signing up..." : "Sign up"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
