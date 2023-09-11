import { Navigate, Outlet, useLocation } from "react-router-dom";

const useAuth = () => {
  const user = window.localStorage.getItem('role');
  return user;
};

const ProtectedRoutes = ({ role }: { role: string }) => {
  const location = useLocation();
  const isAuth = useAuth();
  return isAuth == role ? <Outlet /> : <Navigate to="/" replace state={{ from: location }} />;
};

export default ProtectedRoutes;