import axios from "axios";
import { useEffect, useState } from "react";
import { MdArrowBack, MdArrowForward } from "react-icons/md";
import ReactPaginate from "react-paginate";
import { toast } from "react-toastify";
import { BiCheck, BiX } from 'react-icons/bi';

const itemsPerPage = 10;
type ManualType = {
    sid : number,
    username : string,
    name : string,
    rollnumber : number,
    studentstatus : string,
}

type ManualTypes = ManualType[];

const Manual = ({aid} : {aid : number}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [stdDetails,setStdDetails] = useState<ManualTypes>([]);
  const [action,setAction] = useState('')
  const markAttendance = (url : string) => {
    (async () =>{
        await axios.post(url)
           .then((response) => {
            console.log(response)
             if (response.status === 200){
                setAction(response.data)
             }
             })
           .catch((error) => {
             toast.error(error)
           })
       })()
  }
  useEffect(
    () => {
      if(aid){
        (async () =>{
          await axios.get(`/attendance/report/${aid}/`)
             .then((response) => {
              console.log(response)
               if (response.status === 200) return response;
               else if (response.status === 401 || response.status === 403) {
                 console.error("Invalid username or password");
               } else {
                 console.error(
                   "Something went wrong, try again later or reach out to trevor@coderscampus.com"
                 );
               }
             })
             .then((response) => {
               if (response) {
                 setStdDetails(response.data)    
               }
             })
             .catch((error) => {
               toast.error(error)
             
             })
         })()
      }
       
    
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
        
        <table className="w-11/12 mx-auto border-rounded border border-gray-300 shadow-md">
        <thead>
          <tr className="bg-Aqua-300 text-Black shadow-md ">
          <th className="p-3 text-center">Student ID</th> 
          <th className="p-3 text-center">Student Name</th>
          <th className="p-3 text-center">Roll No.</th>
            <th className="p-3 text-center">Attendance Status</th>
            <th className="p-3 text-center">Actions</th>
          </tr>
        </thead>
        <tbody>
          {paginatedData.map((data, index) => (
            <tr key={index} className={index % 2 === 0 ? 'bg-gray-100' : ''}>
              
              <td className="p-3 text-center">{data.username}</td>
              <td className="p-3 text-center">{data.name}</td>
              <td className="p-3 text-center">{data.rollnumber}</td>
              <td className="p-3 text-center">{data.studentstatus}</td>
              <td className="p-3 text-center">
              <div className='grid grid-cols-3 gap-10'>
              <BiCheck size={30} color="green" onClick={()=>markAttendance(`/attendance/mark/${aid}/${data.sid}`)}/>
              <BiX size={30} color="red" onClick={()=>markAttendance(`/attendance/unmark/${aid}/${data.sid}`)}/>
              </div>          
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      
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