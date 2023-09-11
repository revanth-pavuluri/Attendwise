import { useEffect, useState } from 'react'
import { Progressbars, cardProp } from './Progressbar'
import { useUserdetails } from './UserContext'
import logo from './images/logo.png'
import axios from 'axios'
import { get_stats } from './services/Student'

const Profile = () => {
    const {userdetails} = useUserdetails()
    const [statsdetails,setStatsdetails] = useState<cardProp[]>([])
    useEffect(
      () => {
        get_stats(setStatsdetails);      
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