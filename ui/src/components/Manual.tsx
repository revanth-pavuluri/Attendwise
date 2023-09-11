import axios from "axios";
import { useEffect, useState } from "react";
import { MdArrowBack, MdArrowForward } from "react-icons/md";
import ReactPaginate from "react-paginate";
import { toast } from "react-toastify";
import { BiCheck, BiX } from 'react-icons/bi';
import Table from "./Table";
import { get_attendance_report, mark_attendance } from "./services/Attendance";

const itemsPerPage = 10;
type ManualType = {
    id : number,
    username : string,
    name : string,
    rollnumber : number,
    status : string,
    [key: string]: any;
}

export type ManualTypes = ManualType[];

const Manual = ({aid} : {aid : number}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [stdDetails,setStdDetails] = useState<ManualTypes>([]);
  const [action,setAction] = useState('')
  const columns = [
    { header: 'Student ID', key: 'username' },
    { header: 'Student Name', key: 'name' },
    { header: 'Roll No.', key: 'rollnumber' },
    { header: 'Attendance Status', key: 'status' },
  ];
  const markAttendance = (url : string) => {
    mark_attendance({url,setAction});

  }
  useEffect(
    () => {
      get_attendance_report({aid,setStdDetails})
    
 },[action]);

 const filteredData = stdDetails.filter((data) =>
 data.name.toLowerCase().includes(searchQuery.toLowerCase())

|| data.username.toString().includes(searchQuery.toLowerCase())
 );

 const search = (e :  React.ChangeEvent<HTMLInputElement>) => {
   setSearchQuery(e.target.value)
   setCurrentPage(0)
 }
 
 const startIndex = currentPage * itemsPerPage;
 const endIndex = startIndex + itemsPerPage;
 const paginatedData = filteredData.slice(startIndex, endIndex);
 
 const handlePageChange = (selectedPage: { selected: number }) => {
   setCurrentPage(selectedPage.selected);
 };
  
 const totalPages = Math.ceil(filteredData.length / itemsPerPage);

 return (
    <div className="mt-1">     
       <div className="flex justify-evenly items-center mb-4">
        <label htmlFor="search" className="block ml-6 text-sm font-medium text-gray-700">
          Search by name or ID
        </label>
        <input
          type="text"
          id="search"
          placeholder="Search..."
          value={searchQuery}
          onChange={(e) => search(e)}
          className="mt-1 p-2  ml-5 w-96 border rounded-md focus:ring focus:ring-blue-200"
        />
      </div>
      {filteredData.length != 0 ? (
        <>
        <Table data={paginatedData} columns={columns} actions={{
          datarole : "manualAttendance",
          markattendance : markAttendance,

        }}/>
      
      <div className="mt-4 flex flex-col justify-center items-center">
      <ReactPaginate
          pageCount={totalPages}
          onPageChange={handlePageChange}
          containerClassName="pagination flex flex-row gap-2"
          activeClassName="active font-bold text-lg"
          pageClassName="page-item text-sm text-gray"
          pageLinkClassName="page-link "
          previousLabel={<MdArrowBack className='border rounded border-2 h-8 w-8'/>}
          nextLabel={<MdArrowForward className='border rounded border-2 h-8 w-8'/>}  
        />
        </div>
        </>)
        :<h2 className='text-Black font-xl text-center'>No result found..</h2>}
     
        
      
    </div>
  )
 
}

export default Manual