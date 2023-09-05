
import logo from './images/logo.png';
import menu from './images/menu.png'
import close from './images/close.png'

import { useState } from "react";
import { NavLink, Outlet } from 'react-router-dom';
// import { close, logo, menu } from "../assets";
type navProp = {
  id : string,
  title: string,
}
export type navProps = {
  navLinks : navProp[],
  activeOne : string,
}

export const stdNavs = [
  {
    id: "dashboard",
    title: "Dashboard",
  },
  {
    id: "stats",
    title: "Stats",
  },
  {
    id: "profile",
    title: "Profile",
  },
  {
    id : "logout",
    title : "Logout",
  }
  
];
export const facNavs = [
  {
    id: "dashboard",
    title: "Dashboard",
  },
  {
    id: "students",
    title: "Students",
  },
  // {
  //   id: "profile",
  //   title: "Profile",
  // },
  {
    id : "logout",
    title : "Logout",
  }
  
]

const Navbar = ({navLinks, activeOne} : navProps) => {
  const [active, setActive] = useState(activeOne);
  const [toggle, setToggle] = useState(false);
  
  return (
    <>
    <nav className="w-full flex py-1 p-5 sticky top-0 z-40 justify-between items-center navbar bg-Aqua">
      {/* Logo */}
      <img src = {logo} alt = 'logo' className='h-15 w-12 m-2'></img>
      <div className = 'text-Black text-3xl font-poppins font-bold m-3 '>AttendWise</div>
      
      {/* Desktop Navigation */}
      <ul className="list-none sm:flex m-5 hidden justify-end items-center flex-1">
        {navLinks.map((nav, index) => (
          <li
            key={nav.id}
            className={`font-poppins font-semibold cursor-pointer text-[16px] ${
              active === nav.title ? "text-Black" : "text-Black-100"
            } ${index === navLinks.length - 1 ? "mr-0" : "mr-10"}`}
            
          >
             <NavLink to={nav.id} key={nav.id} onClick={() => setActive(nav.title)}>
                {nav.title}
             </NavLink>
          </li>
        ))}
      </ul>

      {/* Mobile Navigation */}
      <div className="sm:hidden flex flex-1 justify-end items-center">
        <img
          src={toggle ? close : menu}
          alt="menu"
          className="w-[28px] h-[28px] object-contain  m-5"
          onClick={() => setToggle(!toggle)}
        />

        {/* Sidebar */}
        <div
          className={`${
            !toggle ? "hidden" : "flex"
          } p-6 bg-Aqua absolute z-50 top-20 right-0 w-full rounded-lg sidebar`}
        >
          <ul className="list-none flex justify-end items-end flex-1 flex-col">
            {navLinks.map((nav, index) => (
              <li
                key={nav.id}
                className={`font-poppins font-medium cursor-pointer text-[16px] ${
                  active === nav.title ? "text-Black" : "text-Black-100"
                } ${index === navLinks.length - 1 ? "mb-0" : "mb-4"}`}
                
              >
                <NavLink to={nav.id} key={nav.id} onClick={() => setActive(nav.title)}>
                {nav.title}
                </NavLink>
              </li>
            ))}
          </ul>
        </div>
      </div>
      

      
    </nav>
    <Outlet/>
    </>
  );
};

export default Navbar;