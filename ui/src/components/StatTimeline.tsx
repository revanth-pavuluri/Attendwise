import { useParams } from "react-router-dom";

export type StatItemProp = {
    date : string;
    time : string;
    status : string;
}
export type StatItems = {
    items : StatItemProp[];
}

const StatItem =({ time, date , status} : StatItemProp) => {
    return (
      <div className="relative pl-8 sm:pl-32 py-6 group">
         
        <div className="flex flex-col  items-start mb-1 group-last:before:hidden before:absolute before:left-2 sm:before:left-0 before:h-full before:px-px before:bg-slate-300 sm:before:ml-[6.5rem] before:self-start before:-translate-x-1/2 before:translate-y-3 after:absolute after:left-2 sm:after:left-0 after:w-2 after:h-2 after:bg-Yellow after:border-4 after:box-content after:border-slate-50 after:rounded-full sm:after:ml-[6.5rem] after:-translate-x-1/2 after:translate-y-1.5">
          <time className="sm:absolute left-0 translate-y-0.5 inline-flex items-center justify-center text-xs font-semibold uppercase w-20 h-6 mb-3 sm:mb-0 text-Black bg-Yellow-200 rounded-full">{date}</time>
        </div>
        <pre><b>{time.split('T')[1].split('.')[0]}-</b>  Status: <b className={status == 'Present' ? "text-green-700" : "text-Red"}>{status}</b></pre>
        
        
      </div>
    
    );
  }
const StatTimeline = ({items} : StatItems) => {
  return (
    <div className="-my-6" >
    {items.map((item, index) => (
      <StatItem
        key={index}
        time={item.time}
        date={item.date}
        status={item.status}
      />
    ))}
  </div>
  )
}

export default StatTimeline