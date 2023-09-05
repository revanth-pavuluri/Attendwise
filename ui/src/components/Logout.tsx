import { useEffect } from "react";
import { useNavigate } from "react-router-dom"
import { useUserdetails } from "./UserContext";
import { deleteCookie } from "./Login";

const Logout = () => {
    const navigate = useNavigate();
    const {logout} = useUserdetails();
    useEffect(() => {
       logout();
       deleteCookie("LoginToken");
        navigate('/');
      }, [navigate]);
  return (
    <>
    Logging out..
    </>
  )
}

export default Logout