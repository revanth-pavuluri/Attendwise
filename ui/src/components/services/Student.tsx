import axios from "axios";
import { useUserdetails } from "../UserContext";
import { toast } from "react-toastify";
import dayjs from "dayjs";
import { cardProp } from "../Progressbar";
import { StatItemProp } from "../StatTimeline";
import { FinalFacStdProp, FinalFacStdProps } from "../StdTable";

const {userdetails} = useUserdetails();
export const get_student_dashbord = async ({selectDate, setTimelinedetails} : {
    selectDate : dayjs.Dayjs,
    setTimelinedetails : React.Dispatch<React.SetStateAction<never[]>>
}) => {
        await axios.post("student/dashboard", 
       {    
                   "date":selectDate.toDate().toLocaleDateString().split('/').reverse().join('-'),
                   "sid":userdetails?.id,}
       , {
           headers: {
             
           },withCredentials : true
         })
           .then((response) => {
             if (response.status === 200){
               setTimelinedetails(response.data)
               console.log(response)
             }
           })
           .catch((error) => {
               toast.error(error)
           })
}

export const get_stats = async(setStatsdetails :  React.Dispatch<React.SetStateAction<cardProp[]>>) => {
        await axios.get(`/student/stats/${userdetails?.id}`)
        .then((response) => {
          if (response.status === 200){
            setStatsdetails(response.data)
          }
        })
        .catch((error) => {
          toast.error("An error occured during loading")
        })
}

export const get_sub_details = async({subjectState,setsubdetails} : {
    subjectState : string | null,
    setsubdetails : React.Dispatch<React.SetStateAction<StatItemProp[] | undefined>>, 
}) => {
        if(subjectState != null){
        await axios.get(`/student/stats/${userdetails?.id}/${subjectState.split("+")[1]}`)
        .then((response) => {
          if (response.status === 200){
            console.log(response)
            setsubdetails(response.data)
          }
        })
        .catch((error) => {
          toast.error("An error occurred during loading.");
        })
    }
}

export const get_all_students = async(setStudentDetails :  React.Dispatch<React.SetStateAction<FinalFacStdProps>>) => {
        await axios.get(`/student/`)
           .then((response) => {
             if (response.status === 200){
                setStudentDetails(response.data)
             }
           })
           .catch((error) => {
             console.error(error);
             toast.error("An error occurred during login.");
           
           })
}

export const student_add = async({data,handle} : {
    data :  FinalFacStdProp,
    handle : () => void,
}) => {
    await axios.post(`/student/`, data, {
      headers: {

      },withCredentials : true
    })
      .then((response) => {
        if (response.status === 200){
            toast.success("Student data added succesfully!")
            handle();
            console.log(data)
        }
      })
      .catch((error) => {
        toast.error("Failed in creating student data")
      })
}

export const student_edit = async({data,handle} : {
  data :  FinalFacStdProp,
  handle : () => void,
}) => {
  await axios.put(`/student/${userdetails?.id}`, data, {
    headers: {

    },withCredentials : true
  })
    .then((response) => {
      if (response.status === 200){
          toast.success("Student data added succesfully!")
          handle();
          console.log(data)
      }
    })
    .catch((error) => {
      toast.error("Failed in creating student data")
    })
}
