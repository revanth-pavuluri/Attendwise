
import { useUserdetails } from './UserContext'
import logo from './images/logo.png'


const FacProfile = () => {
    const {userdetails} = useUserdetails()
    return(
        <>
    <div className=" m-2 flex flex-col items-center justify-center">
    <div className="bg-Yellow-200 grid grid-cols-5 p-8 rounded-lg shadow-md w-full md:w-2/3 lg:w">
      
      <div className="flex items-left content-center justify-left mb-4">
        <img src={logo} alt="Profile" className="w-20 mt-4 h-20" />
      </div>
      <div className='col-start-2 col-span-4'>
      
    
      <h1 className="text-2xl font-bold mb-2">{userdetails?.name}</h1>
      <p className="text-gray-600 mb-3 font-bold">{userdetails?.role}</p>
      </div>
    </div>
  </div>
  <div className='text-center text-3xl capitalize'>HELLO {userdetails?.name}</div>
  </>
    )
}
export default FacProfile