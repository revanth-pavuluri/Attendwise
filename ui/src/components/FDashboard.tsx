import React, { useEffect, useState } from 'react'
import { MdArrowBack, MdArrowForward, MdDelete, MdEdit } from 'react-icons/md';
import { FaCheck, FaFile} from 'react-icons/fa';
import ReactPaginate from 'react-paginate';
import AttendAddEdit from './AttendAddEdit';
import Modal from './Modal';
import axios from 'axios';
import { useUserdetails } from './UserContext';
import { toast } from 'react-toastify';
import QrGenModal from './QrGenModal';
import Manual from './Manual';

const itemsPerPage = 15;
export type FinalFacDashProp =  {        
    id: number,        
    facultyname: string,            
    facultyid: number,                 
    subjectid: number,            
    subjectname: string,            
    subjectcode: string,                  
    periods: number,        
    status: number,        
    type: string,        
    data: string,        
    classname: string,        
    date: string,        
    createdon: string,        
    updatedon: string
};

type FinalFacDashProps = FinalFacDashProp[];

const FDashboard = () => {

  const {userdetails} = useUserdetails();
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [AddModal, setAddModal] = useState(false);
  const [EditModal,setEditModal] = useState<FinalFacDashProp>();
  const [Editbool,seteditbool] = useState(false)
  const [manual,setManual] = useState<FinalFacDashProp|false>(false)
  const [finalDetails,setFinalDetails] = useState<FinalFacDashProps>([])
  const[finaliseFlag, setFinaliseFlag] = useState(false)
  const Finalise = (id : number) => {
    if(window.confirm('Are you sure to finalize?')==true ){
    (async () =>{
      await axios.post(`/attendance/finalize/${id}`)
         .then((response) => {
          console.log(response)
           if (response.status === 200){
              toast.success("Attendance Finalised!")
              setFinaliseFlag(!finaliseFlag)
           }
         })
         .catch((error) => {
            toast.error(error);
         
         })
     })()
  }
}
const [deleteFlag, setDeleteFlag] = useState(false)
  const OnDelete = (id : number) => {
    if(window.confirm("Are you sure to delete?") == true){
      if(userdetails.id){
        (async () =>{
          await axios.delete(`/attendance/delete/${id}`)
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
  }

  useEffect(
    () => {
        (async () =>{
          await axios.get(`/attendance/f/${userdetails.id}`)
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
                 setFinalDetails(response.data)
                 console.log(response)
                     
               }
             })
             .catch((error) => {
               // Handle errors here
               console.error(error);
               console.error("An error occurred during login.");
             
             })
         })()
  
       
    
 },[AddModal,Editbool,deleteFlag,finaliseFlag,manual,userdetails]);

   

   const filteredData = finalDetails.filter((data) =>
    data.subjectname.toLowerCase().includes(searchQuery.toLowerCase())
  
  // || contact.email.toString().includes(searchQuery.toLowerCase())
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
      
      {Editbool  || AddModal || manual != false ? (
        <Modal body={
          <>
          {manual != false ? <Manual aid = {manual.id}/> : null}
          {AddModal ? <AttendAddEdit handle={()=>setAddModal(false)}/> : null}
          {Editbool ? <AttendAddEdit initialData={EditModal} handle={()=>seteditbool(false)}/> : null} 
        </>
      }
      header = {
        {title : AddModal ? 'Add Attendance Request' : Editbool ? 'Edit Attendance Request' : manual != false ?`${manual.date} - ${manual.classname} - ${manual.subjectname}` : '',
        closehandle : AddModal ? ()=>{setAddModal(false)} : Editbool ? ()=>{seteditbool(false)} : ()=>{setManual(false)}}
      }
      /> 
      ) : null}
      
       <div className="flex justify-evenly items-center mb-4">
        <label htmlFor="search" className="block ml-6 text-sm font-medium text-gray-700">
          Search by Subject name
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
          <th className="p-3 text-center">Date</th> 
          <th className="p-3 text-center">Class Name</th>
            <th className="p-3 text-center">Subject Name</th>
            <th className="p-3 text-center">Periods</th>
            <th className="p-3 text-center">Type</th>
            <th className="p-3 text-center">Status</th>
            <th className="p-3 text-center">Created</th>
            <th className="p-3 text-center">Updated</th>
            <th className="p-3 text-center">Actions</th>
            <th className="p-3 text-center">  </th>
          </tr>
        </thead>
        <tbody>
          {paginatedData.map((data, index) => (
            <tr key={index} className={index % 2 === 0 ? 'bg-gray-100' : ''}>
              
              <td className="p-3 text-center">{data.date}</td>
              <td className="p-3 text-center">{data.classname}</td>
              <td className="p-3 text-center">{data.subjectname}</td>
              <td className="p-3 text-center">{data.periods}</td>
              <td className="p-3 text-center">{data.type}</td>
              <td className="p-3 text-center">{data.status}</td>
              <td className="p-3 text-center">{data.createdon}</td>
              <td className="p-3 text-center">{data.updatedon}</td>
              <td className="p-3 text-center">
              <div className='grid grid-cols-5 gap-2'>
              <MdEdit onClick={()=>
                {setEditModal(data)
                 seteditbool(true)
                }}/>
              <MdDelete onClick={() => OnDelete(data.id)}/>
              <FaFile onClick={() => setManual(data)}/>
              {data.status == 0 ? <><FaCheck onClick={()=>Finalise(data.id)}/> <QrGenModal aid = {data.id}/> </> : ''}
              {data.status == 1 ? <FaCheck onClick={()=>Finalise(data.id)}/> : ''}
              </div>          
              </td>
              <td className="p-3 text-center">
                {data.status == 0 && data.type == 'QR' && 
                <div>
                
                </div>
                }
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

export default FDashboard