import { useEffect } from "react";
import { useNavigate } from "react-router-dom"
import { useUserdetails } from "./UserContext";

const Logout = () => {
    const navigate = useNavigate();
    const {logout} = useUserdetails();
    useEffect(() => {
       logout();
        navigate('/');
      }, [navigate]);
  return (
    <>
    Logging out..
    </>
  )
}

export default Logout