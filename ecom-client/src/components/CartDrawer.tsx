import { FaTimes } from "react-icons/fa";
import CartItem from "./CartItem";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useCreateOrderMutation } from "../apiServices/orderApi";
import toast from "react-hot-toast";
import { clearCart } from "../redux/features/cart/cartSlice";
import { ThreeDot } from "react-loading-indicators";

type CartDrawerProps = {
  setIsCartOpen: (isCartOpen: boolean) => void;
};

const CartDrawer = ({ setIsCartOpen }: CartDrawerProps) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [createOrder,{isLoading}] = useCreateOrderMutation();
  const isCartOpen = true;
  const totalItems = useAppSelector((state) => state.cart.totalQuantity);
  const cartItems = useAppSelector((state) => state.cart.items);

  const isAuthenticated = useAppSelector((state) => state.user.isAuthenticated);
  const handleCheckout = async () => {
    if (isAuthenticated) {
      // Proceed to checkout
      const payload = {
        orderItems: cartItems.map((item) => ({
          productId: item.id,
          quantity: item.quantity,
        })),
      };
      console.log("Checkout payload:", payload);
      const res = await createOrder(payload);
      toast.success("Order placed successfully");
      console.log("Proceeding to checkout...", res);
      setIsCartOpen(false);
      dispatch(clearCart());
      navigate("/my-orders");
    } else {
      // Redirect to login
      toast.success("Login");
      setIsCartOpen(false);
      navigate("/login");
      console.log("Redirecting to login...");
    }
  };

  useEffect(() => {
    if (isCartOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isCartOpen]);
  return (
    <div className="flex absolute w-full h-full z-1">
      {/* overlay */}
      <button
        onClick={() => setIsCartOpen(false)}
        className="touch-none overflow-hidden fixed md:w-1/2  lg:w-3/4 h-full bg-black/40"
      />

      {/* cart drawer */}
      <div className="fixed right-0 top-0 overflow-y-auto md:w-1/2  lg:w-1/4 h-screen bg-white">
        {/* close btn */}
        <button
          onClick={() => setIsCartOpen(false)}
          className="float-end mt-3 mx-3 transition-transform hover:scale-110 duration-300"
        >
          <FaTimes className="size-6" />
        </button>

        {/* cart items */}
        <div className="p-3">
          <h2 className="text-2xl font-bold mb-4">Your Cart ({totalItems})</h2>
          {/* Cart items will go here */}
          {cartItems.length > 0 ? (
            cartItems.map((item) => <CartItem key={item.id} {...item} />)
          ) : (
            <div className="text-center text-gray-500 mt-100 text-3xl">
              Your cart is empty 🛒
              <br />
              Start shopping now!
            </div>
          )}
        </div>

        {/*checkout btn */}
        <div className={`m-3 ${totalItems === 0 ? "hidden" : "block"}`}>
          {isLoading ? (
            <button
              disabled={true}
              className="w-full bg-gray-300  py-3 rounded-md"
            >
              <ThreeDot color="#32cd32" size="medium" text="" textColor="" />
            </button>
          ) : (
            <button
              onClick={handleCheckout}
              className="w-full bg-yellow-300  py-3 rounded-md transition-colors hover:scale-105 hover:bg-yellow-500"
            >
              Proceed to Checkout
            </button>
          )}

          {/* <button
            onClick={handleCheckout}
            className="w-full bg-yellow-300  py-3 rounded-md transition-colors hover:scale-105 hover:bg-yellow-500"
          >
            Proceed to Checkout
          </button> */}
        </div>
      </div>
    </div>
  );
};

export default CartDrawer;
