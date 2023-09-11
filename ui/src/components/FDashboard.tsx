import React, { useEffect, useState } from 'react'
import { MdArrowBack, MdArrowForward} from 'react-icons/md';
import ReactPaginate from 'react-paginate';
import AttendAddEdit from './AttendAddEdit';
import Modal from './Modal';
import axios from 'axios';
import { useUserdetails } from './UserContext';
import { toast } from 'react-toastify';

import Manual from './Manual';
import Table from './Table';
import { attendance_OnDelete, finalise_attendance, get_attendance_details } from './services/Attendance';

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
    updatedon: string,
    [key: string]: any;
};

export type FinalFacDashProps = FinalFacDashProp[];

const FDashboard = () => {

  const {userdetails} = useUserdetails();
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [AddModal, setAddModal] = useState(false);
  const [EditModal,setEditModal] = useState<FinalFacDashProp | false>(false);
  const [manual,setManual] = useState<FinalFacDashProp|false>(false)
  const [finalDetails,setFinalDetails] = useState<FinalFacDashProps>([])
  const[finaliseFlag, setFinaliseFlag] = useState(false)
  const columns = [
    { header: 'Date', key: 'date' },
    { header: 'Class Name', key: 'classname' },
    { header: 'Subject Name', key: 'subjectname' },
    { header: 'Periods', key: 'periods' },
    { header: 'Type', key: 'type' },
    { header: 'Status', key: 'status' },
    { header: 'Created', key: 'createdon' },
    { header: 'Updated', key: 'updatedon' },
  ];
  
  const Finalise = (id : number) => {
    finalise_attendance({id,finaliseFlag,setFinaliseFlag})
}
const [deleteFlag, setDeleteFlag] = useState(false)
  const OnDelete = (id : number) => {
    attendance_OnDelete({id,deleteFlag,setDeleteFlag})
  }

  useEffect(
    () => {
         get_attendance_details(setFinalDetails)
 },[AddModal,EditModal,deleteFlag,finaliseFlag,manual,userdetails]);

   

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
      
      {EditModal != false  || AddModal || manual != false ? (
        <Modal body={
          <>
          {manual != false ? <Manual aid = {manual.id}/> : null}
          {AddModal ? <AttendAddEdit handle={()=>setAddModal(false)}/> : null}
          {EditModal != false ? <AttendAddEdit initialData={EditModal} handle={()=>setEditModal(false)}/> : null} 
        </>
      }
      header = {
        {title : AddModal ? 'Add Attendance Request' : EditModal != false ? 'Edit Attendance Request' : manual != false ?`${manual.date} - ${manual.classname} - ${manual.subjectname}` : '',
        closehandle : AddModal ? ()=>{setAddModal(false)} : EditModal ? ()=>{setEditModal(false)} : ()=>{setManual(false)}}
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
        <Table data = {paginatedData}
        columns={columns} actions={{
          datarole : "attendance",
          editattend : setEditModal,
          delete : OnDelete,
          manual : setManual,
          final : Finalise,
        }} />
      
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