import Card from "./Card";
import QrModal from "./QrModal";


export type TimelineItemProps = {
    aid : number,
    facultyname :string,
    subjectname : string,
    subjectcode : string,
    periods : number,
    status : number,
    type : string,
    studentstatus : string,
    createdon : string,
    updatedon : string,
}

export type TimelineProps = {
    timelineData: TimelineItemProps[];
  }

const TimelineItem =({ ...TimelineItem} : TimelineItemProps) => {
    return (
      <div className="relative pl-8 sm:pl-32 py-6 group">
         
        <div className="flex flex-col  items-start mb-1 group-last:before:hidden before:absolute before:left-2 sm:before:left-0 before:h-full before:px-px before:bg-slate-300 sm:before:ml-[6.5rem] before:self-start before:-translate-x-1/2 before:translate-y-3 after:absolute after:left-2 sm:after:left-0 after:w-2 after:h-2 after:bg-Yellow after:border-4 after:box-content after:border-slate-50 after:rounded-full sm:after:ml-[6.5rem] after:-translate-x-1/2 after:translate-y-1.5">
          <time className="sm:absolute left-0 translate-y-0.5 inline-flex items-center justify-center text-xs font-semibold uppercase w-20 h-6 mb-3 sm:mb-0 text-Black bg-Yellow-200 rounded-full">{TimelineItem.createdon.split("T")[1].split(".")[0]}</time>
        </div>
        <Card {...TimelineItem}
        />
      </div>
    
    );
  }

const Timeline = ({timelineData} : TimelineProps) => {
  return (
    <div className="-my-6">
      
    {timelineData.map((item, index) => (
      <TimelineItem
      key={index}
        {...item}
      />
    ))}
    {timelineData.length === 0 && <div className="text-2xl p-10 text-Red font-semibold">No classes..</div>}
  </div>
  )
}

export default Timeline