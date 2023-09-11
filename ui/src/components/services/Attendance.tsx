import axios from "axios";
import { toast } from "react-toastify";
import { FinalFacDashProp, FinalFacDashProps } from "../FDashboard";
import React from "react";
import { useUserdetails } from "../UserContext";
import { ManualTypes } from "../Manual";

const {userdetails} = useUserdetails();
export const attend_add = async({data, handle} : {
    data : FinalFacDashProp,
    handle : ()=>void,
}) => {
    await axios.post(`/attendance/`, data, {
      headers: {
      },withCredentials : true
    })
      .then((response) => {
        if (response.status === 200) {
          toast.success("Request added succesfully!")
          handle();
          console.log(data)
        }
      })
      .catch((error) => {
        toast.error("Failed in updating attendance request")
      })
}
export const attend_edit = async({data, handle} : {
  data : FinalFacDashProp,
  handle : ()=>void,
}) => {
  await axios.put(`/attendance/${data.id}`, data, {
    headers: {
    },withCredentials : true
  })
    .then((response) => {
      if (response.status === 200) {
        toast.success("Request added succesfully!")
        handle();
        console.log(data)
      }
    })
    .catch((error) => {
      toast.error("Failed in updating attendance request")
    })
}
export const finalise_attendance = async({id, finaliseFlag, setFinaliseFlag}:{
    id: number,
    finaliseFlag : boolean,
    setFinaliseFlag : React.Dispatch<React.SetStateAction<boolean>>,

}) => {
    if(window.confirm('Are you sure to finalize?')==true ){
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
  }
}

export  const attendance_OnDelete = async({id, deleteFlag,setDeleteFlag} : {
    id : number,
    deleteFlag : boolean,
    setDeleteFlag : React.Dispatch<React.SetStateAction<boolean>>,
}) => {
    if(window.confirm("Are you sure to delete?") == true){
      if(userdetails.id){
          await axios.delete(`/attendance/${id}`)
             .then((response) => {
               if (response.status === 200){
                  toast.success("Deleted successfully!")
                  setDeleteFlag(!deleteFlag)
               }
             })
             .catch((error) => {
                toast.error(error);
             
             })
      }
    }
  }

  export const get_attendance_details = async(setFinalDetails :  React.Dispatch<React.SetStateAction<FinalFacDashProps>>) => {
    await axios.post(`/attendance/all`,{"faculty_id":userdetails?.id})
       .then((response) => {
         if (response.status === 200){
            setFinalDetails(response.data)
            console.log(response)
         }
        
       })
       .catch((error) => {
         console.error(error);
         toast.error("An error occurred during login.");
       
       })
   }

export const get_attendance_report = async({aid,setStdDetails} : {
    aid:number,
    setStdDetails :  React.Dispatch<React.SetStateAction<ManualTypes>>,
}) => {
    if(aid){
          await axios.get(`/attendance/report/${aid}/`)
             .then((response) => {
              console.log(response)
               if (response.status === 200){
                setStdDetails(response.data)
               }
             })
             .catch((error) => {
               toast.error(error)
             
             })
      }
}

export const mark_attendance = async({url, setAction} : {
    url : string,
    setAction :  React.Dispatch<React.SetStateAction<string>>,
}) => {
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
}

export const mark_with_QR = async({id,setShowModal} : {
  id : number,
  setShowModal : React.Dispatch<React.SetStateAction<boolean>>,
}) => {
    await axios.post(`/attendance/mark/${id}/${userdetails.id}`)
       .then((response) => {
         if (response.status === 200){
            setShowModal(false)  
            toast.success ("Attendance marked successfully!")   
         }
       })
       .catch((error) => {
        if (error.status === 401 || error.status === 403 || error.status === 400) {
          toast.error("Error occured, Try again!!!");
        } else if(error.status === 404 || error.status === 504 || error.status === 423 ) {
          toast.error(error);
        }
         
       
       })
} 