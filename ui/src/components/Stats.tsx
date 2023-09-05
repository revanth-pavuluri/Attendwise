import { useEffect, useState } from "react"
import CircleProgress from "./CircleProgress"
import Progressbar from "./Progressbar"
import StatTimeline, { StatItemProp} from "./StatTimeline"
import Modal from "./Modal"
import axios from "axios"
import { useUserdetails } from "./UserContext"



const Stats = () => {
    const [subjectState,setSubjectState] = useState<string | null>(null);
    const [subdetails,setsubdetails] = useState<StatItemProp[]>()
    const {userdetails} = useUserdetails()
    const [statsdetails,setStatsdetails] = useState<{subjectname : string, percentage:number,subid : number}[]>()
    useEffect(
      () => {
        ( async() => {
          await axios.get(`/student/stats/${userdetails?.id}`)
          .then((response) => {
            if (response.status === 200) return response;
            else if (response.status === 401 || response.status === 403) {
              console.error(" 401 or 403 error");
            } else {
              console.error(
                "Something went wrong, try again later"
              );
            }
          })
          .then((data) => {
            if (data) {
              console.log(data)
              setStatsdetails(data.data)
            }
          })
          .catch((error) => {
            // Handle errors here
            console.error(error);
            console.error("An error occurred during loading.");
          })
        })()
      
      },[userdetails]);  
    useEffect(
      () => {
        (async() => {
          if(subjectState != null){
          await axios.get(`/student/stats/${userdetails?.id}/${subjectState.split("+")[1]}`)
          .then((response) => {
            if (response.status === 200) return response;
            else if (response.status === 401 || response.status === 403) {
              console.error(" 401 or 403 error");
            } else {
              console.error(
                "Something went wrong, try again later"
              );
            }
          })
          .then((data) => {
            if (data) {
              console.log(data)
              setsubdetails(data.data)
            }
          })
          .catch((error) => {
            // Handle errors here
            console.error(error);
            console.error("An error occurred during loading.");
          })
        }})()
      
      },[subjectState,userdetails]);
      const [percentage,setPercentage] = useState<number>(0);
      let temp = 0
      useEffect(
        () => {
          if(statsdetails){
            statsdetails.map((data,index) => (
              temp += data.percentage
          ))
          console.log("Total"+temp)
          setPercentage(temp / statsdetails.length)
          console.log("Avg"+percentage)
        }
        
        },[statsdetails,userdetails]);
    return(
        <>
        { subjectState ? (
          <Modal header = {{
            title : subjectState.split("+")[0],
            closehandle : ()=>{setSubjectState(null)},
          }} body = {<StatTimeline items={subdetails ? subdetails : []}/>}/>
      ) : null}
      {statsdetails &&
      <>
        <CircleProgress percent = {percentage}/>
        <div className="container mx-auto p-4">
        <div className="flex flex-wrap justify-evenly">
       { statsdetails.map((card,index) => (
          < Progressbar
            key = {index}
            {...card}
            handle = {() => setSubjectState(`${card.subjectname}+${card.subid}`)}
          />
        ))}
      </div>
  </div>
  </>}
  </>
    )
}
export default Stats