import React, { useEffect, useState } from 'react'
import { MdArrowBack, MdArrowForward, MdDelete, MdEdit } from 'react-icons/md';
import ReactPaginate from 'react-paginate';
import AddEdit from './AttendAddEdit';
import Modal from './Modal';
import axios from 'axios';
import { useUserdetails } from './UserContext';
import { toast } from 'react-toastify';
import StdTableAddEdit from './StdTableAddEdit';

const itemsPerPage = 15;
type FacStdProp =  {        
    id: number,
    username: string,            
    name: string,            
    device : string,            
    rollnumber: number,
    classname : string,           
    createdon: string,            
    updatedon: string          
    
}[];
export type FinalFacStdProp = {
    id : number,
    username : string,
    name : string;
    rollnumber : number;
    classname : string;
};
type FinalFacStdProps = FinalFacStdProp[];


const StdTable = () => {
    const [deleteFlag, setDeleteFlag] = useState(false)
    const OnDelete = (id : number) => {
      if(userdetails.id){
        (async () =>{
          await axios.delete(`/student/delete/${id}`)
             .then((response) => {
               if (response.status === 200){
                  toast.success("Deleted successfully!")
                  setDeleteFlag(!deleteFlag)
               }
             })
             .catch((error) => {
                toast.error(error);
             
             })
         })()
      }
      
    }
  const {userdetails} = useUserdetails();
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [AddModal, setAddModal] = useState(false);
  const [EditModal,setEditModal] = useState<FinalFacStdProp>();
  const [Editbool,seteditbool] = useState(false)
  const [studentDetails,setStudentDetails] = useState<FinalFacStdProps>([])
  useEffect(
    () => {
        (async () =>{
         await axios.get(`/student/all`)
            .then((response) => {
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
                setStudentDetails(response.data)            
              }
            })
            .catch((error) => {
              // Handle errors here
              console.error(error);
              console.error("An error occurred during login.");
            
            })
        })()
    
 },[AddModal,Editbool,deleteFlag]);


   

   const filteredData = studentDetails.filter((data) =>
    data.username.toLowerCase().includes(searchQuery.toLowerCase())
    || data.name.toString().includes(searchQuery.toLowerCase())
    || data.classname.toString().includes(searchQuery.toLowerCase())
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
      
      {Editbool  || AddModal ? (
        <Modal body={
          <>
          {AddModal ? <StdTableAddEdit handle={()=>{setAddModal(false)}}/> : null}
          {Editbool ? <StdTableAddEdit handle ={()=>seteditbool(false)} initialData={EditModal}/> : null} 
        </>
      }
      header = {
        {title : AddModal ? 'Add data' : 'Edit data',
        closehandle : AddModal ? ()=>{setAddModal(false)} : ()=>{seteditbool(false)}}
      }
      /> 
      ) : null}
      
       <div className="flex justify-evenly items-center mb-4">
        <label htmlFor="search" className="block ml-6 text-sm font-medium text-gray-700">
          Search 
        </label>
        <input
          type="text"
          id="search"
          placeholder="Search..."
          value={searchQuery}
          onChange={(e) => search(e)}
          className="mt-1 p-2  ml-5 w-96 border rounded-md focus:ring focus:ring-blue-200"
        />
        
          <button className="bg-Aqua-300 text-Black font-medium m-1 py-2 px-4 rounded-md hover:font-bold ml-2"
            onClick={() => setAddModal(true)}>
            ADD
          </button>
          
         
      </div>
      {filteredData.length != 0 ? (
        <>
        
        <table className="w-11/12 mx-auto border-rounded border border-gray-300 shadow-md">
        <thead>
          <tr className="bg-Aqua-300 text-Black shadow-md ">
            <th className="p-3 text-center">Student ID</th>
            <th className="p-3 text-center">Name</th>
            <th className="p-3 text-center">Roll no.</th>
            <th className="p-3 text-center">Class</th>
            <th className="p-3 text-center">Actions</th>
          </tr>
        </thead>
        <tbody>
          {paginatedData.map((data, index) => (
            <tr key={index} className={index % 2 === 0 ? 'bg-gray-100' : ''}>
              <td className="p-3 text-center">{data.username}</td>
              <td className="p-3 text-center">{data.name}</td>
              <td className="p-3 text-center">{data.rollnumber}</td>
              <td className="p-3 text-center">{data.classname}</td>
              <td className="p-3 text-center">
              <div className='grid grid-cols-2 gap-3'>
              <MdEdit onClick={()=>
                {setEditModal(data)
                 seteditbool(true)
                }}/>
              <MdDelete onClick={() => OnDelete(data.id)}/>
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

export default StdTable