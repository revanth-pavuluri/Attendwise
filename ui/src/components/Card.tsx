import QrGenModal from "./QrGenModal";
import QrModal from "./QrModal";
import { TimelineItemProps } from "./Timeline";
const Card = ({aid,facultyname,subjectname,subjectcode,periods,status,type,studentstatus,updatedon}:TimelineItemProps) => {
  return (
    <div className="max-w-sm  w-full  p-3 bg-white border border-gray-200 m-2 rounded-lg shadow ">
    <div className="relative pt-1 p-3">
<div className="flex mb-2 items-center justify-between">
<div>
 <span className="text-xl font-semibold inline-block py-1 px-2 uppercase rounded-full text-Black bg-Yellow-200">
    {subjectcode}
 </span>
</div>
<div className="text-right">
 <span className="text-xs font-semibold inline-block text-">
    {status === 0 && type === 'QR' && studentstatus !== "Present" ? <QrModal id = {aid}/> : status === 0 && type === 'QR' && studentstatus == "Present" ? <QrGenModal aid = {aid}/> : null}
    {status == 1 ? 'Expired' : status == 2 ? 'Finalized' : null}
   
 </span>
</div>
</div>
    <span className="text-bold">{subjectname}</span><br/>
    Periods : <span className="text-bold">{periods}</span><br/>
    Faculty : <span className="text-bold">{facultyname}</span><br/>
    {studentstatus}<br/>
    Updated On : {updatedon.split("T")[1].split(".")[0]}
</div>
</div>  
  )
}

export default Card