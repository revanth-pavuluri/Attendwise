import './App.css';
import {Routes,Route, Navigate} from 'react-router-dom'
import NavBar, { facNavs, stdNavs } from './components/NavBar';
import Dashboard from './components/Dashboard';
import Stats from './components/Stats';
import Profile from './components/Profile';
import Login from './components/Login';
import PageNotFound from './components/PageNotFound';
import FDashboard from './components/FDashboard';
import StdTable from './components/StdTable';
import FacProfile from './components/FacProfile';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css';
import Logout from './components/Logout';

import ProtectedRoutes from './components/ProtectedRoutes';



function App() {
  return (
    <div >
      <ToastContainer/>
    <Routes>
      <Route path="/" element={<Login/>}/>

      <Route element = {<ProtectedRoutes role = "student"/>}>
        
        <Route path = 'u' element={<NavBar navLinks={stdNavs} activeOne='Dashboard'/>}>
        <Route index element={<Dashboard/>}/>
        <Route path='dashboard' element = {<Dashboard/>}/>
        <Route path = 'stats' element = {<Stats/>}/>
        <Route path = 'profile' element = {<Profile/>}/>
        <Route path = 'logout' element = {<Logout/>}/>
        </Route>
      </Route>
      
      <Route element = {<ProtectedRoutes role = "faculty"/>}>
      <Route path = 'f' element={<NavBar navLinks={facNavs} activeOne='Dashboard'/>}>
        <Route index element={<FDashboard/>}/>
        <Route path='dashboard' element = {<FDashboard/>}/>
        <Route path = 'students' element = {<StdTable/>}/> 
        {/* <Route path = 'profile' element = {<FacProfile/>}/> */}
        <Route path = 'logout' element = {<Logout/>}/>
      </Route>
      </Route>
      

      <Route path = '*' element = {<PageNotFound/>}/>
    </Routes>
    
    </div>
  );
}

export default App;
