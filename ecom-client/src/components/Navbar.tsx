import { Link, useNavigate } from "react-router-dom";
import { FaBoxOpen } from "react-icons/fa6";
import { FaShoppingCart } from "react-icons/fa";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
import { logout } from "../redux/features/user/userSlice";
import { orderApi } from "../apiServices/orderApi";

type NavbarProps = {
  setIsCartOpen: (isCartOpen: boolean) => void;
};

const Navbar = ({ setIsCartOpen }: NavbarProps) => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const isAuthenticated = useAppSelector((state) => state.user.isAuthenticated);
  const totalItems = useAppSelector((state) => state.cart.totalQuantity);

  const handleLogout = () => {
    // Implement logout functionality here
    dispatch(logout());
    dispatch(orderApi.util.resetApiState());
    navigate("/login");
  };
  return (
    <nav className="bg-white border-b shadow-sm">
      <div className="mx-6 px-6 h-16 flex items-center justify-between">
        {/* Logo */}
        <Link to="/" className="text-2xl font-bold">
          <FaBoxOpen className="inline-block mr-2 size-10" />
          Ecom
        </Link>

        {/* Links */}
        {/* <div className="flex items-center gap-6">
          {NAV_LINKS.map((link) => (
            <NavLink
              to={link.path}
              className={({ isActive }) =>
                `px-3 py-2 rounded-md text-xl font-medium
         ${isActive ? "text-blue-600" : "text-gray-600 hover:text-blue-500"}`
              }
            >
              {link.label}
            </NavLink>
          ))}
        </div> */}
        {/* Auth Actions */}
        <div className="flex items-center gap-3">
          {!isAuthenticated ? (
            <>
              <Link
                to={"/login"}
                className="text-xl text-gray-600 hover:text-blue-600"
              >
                Login
              </Link>
              <Link
                to="/register"
                className="text-xl text-gray-600 hover:text-blue-600"
              >
                Signup
              </Link>

              {/* <Link
                to="/my-orders"
                className="text-xl text-gray-600 hover:text-blue-600"
              >
                MyOrders
              </Link> */}

              <button
                onClick={() => setIsCartOpen(true)}
                className="cursor-pointer relative rounded-full p-3 hover:bg-gray-100 select-none"
              >
                <FaShoppingCart className="size-5" />
                <span className="text-md absolute rounded-full  top-0 right-0">
                  {totalItems}
                </span>
              </button>
            </>
          ) : (
            <>
              <button
                onClick={handleLogout}
                className="text-xl text-red-500 hover:text-red-600"
              >
                Logout
              </button>
              <Link
                to="/my-orders"
                className="text-xl text-gray-600 hover:text-blue-600"
              >
                {/* <PiVanDuotone size={26} /> */}
                MyOrders
              </Link>
              <button
                onClick={() => setIsCartOpen(true)}
                className="cursor-pointer relative rounded-full p-3 hover:bg-gray-100 select-none"
              >
                <FaShoppingCart className="size-5" />
                <span className="text-md absolute rounded-full  top-0 right-0">
                  {totalItems}
                </span>
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
