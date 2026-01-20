import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";

import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import CartDrawer from "./components/CartDrawer";
import { useState } from "react";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProtectedRoute from "./utils/route/ProtectedRoute";
import MyOrders from "./pages/MyOrders";
import Payment from "./pages/Payment";
import DummyGateway from "./pages/DummyGateway";
import PaymentCallback from "./pages/PaymentCallback";

function App() {
  const [isCartOpen, setIsCartOpen] = useState(false);
  return (
    <BrowserRouter>
      {isCartOpen && <CartDrawer setIsCartOpen={setIsCartOpen} />}

      <Navbar setIsCartOpen={setIsCartOpen} />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route element={<ProtectedRoute />}>
          {/* Protected routes go here */}
          <Route path="/" element={<Home />} />
          <Route path="/my-orders" element={<MyOrders />} />
          <Route path="/payment/:orderId" element={<Payment />} />
          <Route path="/payment-gateway" element={<DummyGateway />} />
          
          <Route path="/payment-callback" element={<PaymentCallback />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
