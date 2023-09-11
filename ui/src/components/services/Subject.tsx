import axios from "axios";
import { toast } from "react-toastify";
import { subTypes } from "../AttendAddEdit";

export const get_all_subjects = async(SetSubdetails : React.Dispatch<React.SetStateAction<subTypes>>) => {
        await axios.get(`/subject/`)
           .then((response) => {
             if (response.status === 200){
               SetSubdetails(response.data);
             };
            
           })
           .catch((error) => {
             toast.error("Subject loading failed")
           })
}