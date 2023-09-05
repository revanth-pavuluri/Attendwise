import axios, { AxiosResponse } from "axios";
import { ReactNode, createContext, useContext, useEffect, useState } from "react";
import { getCookie } from "./Login";
type UserDetailsType = {
  id : number,
  name : string,
  role : string,
  updatedon : string,
  username : string,
  createdon : string,
  rollnumber : string,
  classname : string,
}
interface UserdetailsType{
    userdetails : UserDetailsType;
    setUserdetails: React.Dispatch<React.SetStateAction<UserDetailsType>>;
    logout: () => void;
  } 
const Userdetails = createContext<UserdetailsType | undefined>(undefined);

const logout = () => {
  // Clear user details from session storage and state
  console.log("Before session",window.localStorage.getItem("userdeets"))
  window.localStorage.clear();
  delete axios.defaults.headers.common["Login"]
  console.log("After session",window.localStorage.getItem("userdeets"))
};

export const UserdetailsProvider = ({children} : {children : ReactNode}) => {
    const [userdetails, setUserdetails] = useState<UserDetailsType >({} as UserDetailsType);
   
    useEffect(()=>{
      const storedData = window.localStorage.getItem("userdeets")
      
      if(storedData){            
        const data : UserDetailsType = JSON.parse(storedData);
        console.log("Userdetails",data)
        var cookie =  getCookie("LoginToken")
        axios.defaults.headers.common["Login"] = `Bearer ${cookie}`         
        setUserdetails(data)
      }},[]);
      useEffect(() => {
        console.log(userdetails.id);
      }, [userdetails]);
    return (
      <Userdetails.Provider value={{userdetails, setUserdetails, logout}}>
        {children}
      </Userdetails.Provider>
    );
  };
  export const useUserdetails = () => {
    const userdetails = useContext(Userdetails);
    if (userdetails === undefined) {
      throw new Error('useUserdetails must be inside a UserdetailsProvider');
    }
    return userdetails;
  };
