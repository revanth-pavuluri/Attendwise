import { useEffect, useState } from 'react'
import { Progressbars } from './Progressbar'
import { useUserdetails } from './UserContext'
import logo from './images/logo.png'
import axios from 'axios'

const Profile = () => {
    const {userdetails} = useUserdetails()
    const [statsdetails,setStatsdetails] = useState([])
    useEffect(
      () => {
        (async() => {
          await axios.get(`/student/stats/${userdetails?.id}`)
          .then((response) => {
            if (response.status === 200) return response;
            else if (response.status === 401 || response.status === 403) {
              console.error(" 401 or 403 error");
            } else {
              console.error(
                "Something went wrong, try again later"
              );
            }
          })
          .then((data) => {
            if (data) {
              console.log(data)
              setStatsdetails(data.data)
            }
          })
          .catch((error) => {
            // Handle errors here
            console.error(error);
            console.error("An error occurred during loading.");
          })
        })()
      
      },[]);
    return(
        <>
    <div className=" m-2 flex flex-col items-center justify-center">
    <div className="bg-Yellow-200 grid grid-cols-5 p-8 rounded-lg shadow-md w-full md:w-2/3 lg:w">
      
      <div className="flex items-left content-center justify-left mb-4">
        <img src={logo} alt="Profile" className="w-20 mt-4 h-20" />
      </div>
      <div className='col-start-2 col-span-4'>
      
    
      <h1 className="text-2xl font-bold mb-2">{userdetails?.name} - {userdetails?.username}</h1>
      <p className="text-gray-600 mb-3 font-bold">{userdetails?.classname}</p>
      <p className="text-gray-700"> Roll No : <b>{userdetails?.rollnumber}</b></p>
    
      </div>
    </div>
  </div>
  <div className="container mx-auto p-4">
  <Progressbars bar={statsdetails}/>
  </div>
  </>
    )
}
export default Profile