import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = () => {
  // const isAuthenticated = useAppSelector((state) => state.user.isAuthenticated);
  const isAuthenticated = true;
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoute;
