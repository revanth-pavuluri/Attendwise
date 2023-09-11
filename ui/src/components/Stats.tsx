import { useEffect, useState } from "react"
import CircleProgress from "./CircleProgress"
import Progressbar, { cardProp } from "./Progressbar"
import StatTimeline, { StatItemProp} from "./StatTimeline"
import Modal from "./Modal"
import axios from "axios"
import { useUserdetails } from "./UserContext"
import { get_stats, get_sub_details } from "./services/Student"



const Stats = () => {
    const [subjectState,setSubjectState] = useState<string | null>(null);
    const [subdetails,setsubdetails] = useState<StatItemProp[]>()
    const {userdetails} = useUserdetails()
    const [statsdetails,setStatsdetails] = useState<cardProp[]>([])
    useEffect(
      () => {
        get_stats(setStatsdetails)
      
      },[userdetails]);  
    useEffect(
      () => {
        get_sub_details({subjectState,setsubdetails})
      
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