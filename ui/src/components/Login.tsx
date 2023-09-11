
import React, { createContext, useContext, useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import logo from './images/logo.png'
import { object, string } from 'yup'
import { yupResolver } from '@hookform/resolvers/yup'
import { useLocation, useNavigate } from 'react-router-dom'
import axios from 'axios'
import { useUserdetails } from './UserContext'
import { toast } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.min.css';



const Login= () => {  
  
  const {setUserdetails} = useUserdetails();
  
  const location = useLocation();
  // const [jwt, setJwt] = useLocalState
  const navigate = useNavigate();
  const validationSchema = object().shape({
    username: string()
      .required('Username is required')
      .min(3, 'Username must be at least 3 characters'),
    password: string()
      .required('Password is required')
      .min(4, 'Password must be at least 4 characters'),
  })

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(validationSchema),
  })
  // if(window.localStorage.getItem('jwt')){
  //   console.log('jwt found')
    if(window.localStorage.getItem('role') == "student"){
      console.log("student page");
      navigate('/u/dashboard');
    }
    if(window.localStorage.getItem('role') == "faculty"){
      console.log("faculty page");
      navigate('/f/dashboard');
    }
  // }
  
  const onSubmit = async (data : any) => {
  
    console.log(data)
  
    const reqBody = data;
    axios.defaults.withCredentials = true;
    await axios.post("/login", reqBody, { })
      .then((response) => {
        console.log(response);
        if (response.status === 200) return response;
      })
      .then((data) => {
        if (data) {
          setUserdetails(data.data)
          window.localStorage.setItem("userdeets",JSON.stringify(data.data))
          window.localStorage.setItem("Jwt",data.data['password'])
          axios.defaults.headers.common["Login"] = `Bearer ${data.data['password']}` 
          console.log(data)
          console.log(data.data['password'])
          
          if(data.data['role'] == "Prof"){
            console.log(data.data["role"])
            window.localStorage.setItem("role","faculty")
            navigate("/f/dashboard");
          }else{
            console.log(data.data["role"])
            window.localStorage.setItem("role","student")
            navigate("/u/dashboard");
          }
        }
      })
      .catch((error) => {
        toast.error('Bad Credentials');

      })
     
  }
  return (
    <>
    <div className="flex min-h-full w-11/12 justify-center mx-auto md:w-2/4 sm:w-3/4 flex-1 flex-col border rounded shadow-md m-10 px-6 py-12 lg:px-8">
    <div className="sm:mx-auto sm:w-full sm:max-w-sm">
      <img
        className="mx-auto h-14 w-auto"
        src={logo}
        alt="AttendWise"
      />
      <h2 className="mt-10 text-center text-3xl font-bold leading-9 tracking-tight text-Black">
        Welcome to AttendWise
      </h2>
      <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-Black">
        Sign in to your account
      </h2>
    </div>

    <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
    {/* action="#" method="POST"  */}
      <form className="space-y-6" onSubmit={handleSubmit(onSubmit)} >
      <div>
          <label htmlFor="username" className="block text-sm font-medium leading-6 text-Black">
            User name
          </label>
          <div className="mt-2">
            <input
              id="username"
              type="text"{...register('username')}
              required
              className="block w-full p-2 rounded-md border-0 py-1.5 text-Black shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400  focus:ring-inset focus:ring-2 focus:ring-Black-200 sm:text-sm sm:leading-6"
            />
          </div>
          {errors.username && (
            <p className="text-xs italic text-Red">{errors.username.message}</p>
          )}
        </div>

        <div>
          <div className="flex items-center justify-between">
            <label htmlFor="password" className="block text-sm font-medium leading-6 text-Black">
              Password
            </label>
          </div>
          <div className="mt-2">
            <input
              id="password"
              type="password"{...register('password')}
              autoComplete="current-password"
              required
              className="block w-full rounded-md border-0 py-1.5 p-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-Black-200 sm:text-sm sm:leading-6"
            />
          </div>
        </div>
        {errors.password && (
            <p className="text-xs italic text-red-500">{errors.password.message}</p>
          )}
        <div>
          <button
            type="submit"
            className="flex w-full justify-center rounded-md bg-Aqua-400 px-3 py-1.5 text-sm font-semibold leading-6 text-Black shadow-sm hover:bg-Aqua-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-Aqua-500"
          >
            Sign in
          </button>
        </div>
      </form>
    </div>
  </div>
</>
  )
}

export default Login
