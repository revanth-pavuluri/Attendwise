import React, { ReactNode } from 'react';
import { FinalFacDashProp, FinalFacDashProps } from './FDashboard';
import { FinalFacStdProp, FinalFacStdProps } from './StdTable';
import { MdDelete, MdEdit } from 'react-icons/md';
import QrGenModal from './QrGenModal';
import { FaCheck, FaFile } from 'react-icons/fa';
import { ManualTypes } from './Manual';
import { BiCheck, BiX } from 'react-icons/bi';
type tableProps = {
    data : FinalFacDashProps | FinalFacStdProps | ManualTypes,
    columns : {
        header : string,
        key : string,
    }[],
    actions : { 
        datarole : string,
        editstd ?: React.Dispatch<React.SetStateAction<false | FinalFacStdProp | undefined>> ,
        delete ?: (id : number) => void,
        editattend ?: React.Dispatch<React.SetStateAction<false | FinalFacDashProp>>,
        manual ?: React.Dispatch<React.SetStateAction<false | FinalFacDashProp>>,
        final ?: (id : number) => void,
        markattendance ?: (url : string) => void,
        aid ?: number,

}
}
const Table = ({ data, columns, actions } : tableProps) => {
  return (
    <table className="w-11/12 mx-auto border-rounded border border-gray-300 shadow-md">
    <thead>
    <tr className="bg-Aqua-300 text-Black shadow-md">
          {columns.map((column, index) => (
            <th key={index} className="p-3 text-center">
              {column.header}
            </th>
          ))}
          <th className="p-3 text-center">Actions</th>
        </tr>
    </thead>
    <tbody>
        {data.map((row, rowIndex) => (
          <tr key={rowIndex} className={rowIndex % 2 === 0 ? 'bg-gray-100' : ''}>
            {columns.map((column, colIndex) => (
              <td key={colIndex} className="p-3 text-center">
                {row[column.key]}
              </td>
            ))}
              <td className="p-3 text-center">
                {actions.datarole == "student" && <div className='grid grid-cols-2 gap-3'>
              <MdEdit onClick={() => actions.editstd ? actions.editstd(row as FinalFacStdProp) : ()=>{}}/>
              <MdDelete onClick={() => actions.delete ? actions.delete(row.id) : () => {}}/>
              </div>}
              {actions.datarole == "attendance" && <div className='grid grid-cols-5 gap-2'>
              <MdEdit onClick={()=>
                {actions.editattend ? actions.editattend(row as FinalFacDashProp) : ()=>{}
                }}/>
              <MdDelete onClick={() => actions.delete ? actions.delete(row.id) : () => {}}/>
              <FaFile onClick={() => actions.manual ? actions.manual(row as FinalFacDashProp) : ()=>{} }/>
              {row.status == 0 ? <><FaCheck onClick={()=>actions.final ? actions.final(row.id) : ()=>{}}/> <QrGenModal aid = {row.id}/> </> : ''}
              {row.status == 1 ? <FaCheck onClick={()=>actions.final ? actions.final(row.id) : ()=>{}}/> : ''}
              </div>  }
              {actions.datarole == "manualAttendance" && <div className='grid grid-cols-3 gap-10'>
              <BiCheck size={30} color="green" onClick={()=> actions.markattendance ? actions.markattendance(`/attendance/mark/${actions.aid}/${row.id}`) : () => {}}/>
              <BiX size={30} color="red" onClick={()=> actions.markattendance ? actions.markattendance(`/attendance/unmark/${actions.aid}/${row.id}`) : () => {}}/>
              </div>   }
              </td>
          </tr>
        ))}
      </tbody>
  </table>
  );
};

export default Table;
