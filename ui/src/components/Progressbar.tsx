import { useNavigate } from "react-router-dom"

export type cardProp = {
    subjectname : string,
    percentage : number,
    subid : number,
    handle ?: ()=>void,
}
type bars = {
    bar : cardProp[],
    
}
export const Progressbar = ({subjectname, percentage,subid,handle} : cardProp) => {
 
  return (
    <div className="max-w-sm w-full md:w-1/3 p-3 bg-white border border-gray-200 m-2 rounded-lg shadow " onClick={handle}>
         <div className="relative pt-1 p-3">
  <div className="flex mb-2 items-center justify-between">
    <div>
      <span className="text-xs font-semibold inline-block py-1 px-2 uppercase rounded-full text-Black bg-Yellow-200">
        {subjectname} - {subid}
      </span>
    </div>
    <div className="text-right">
      <span className="text-xs font-semibold inline-block text-Black">
        {percentage}%
      </span>
    </div>
  </div>
  <div className="overflow-hidden h-2  text-xs mt-4 flex rounded bg-Yellow-200">
    <div style={{ width: `${percentage}%` }} className="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-Yellow"></div>
  </div>
</div>
    </div>  
  )
}
export const Progressbars = ({bar} : bars) => {

    return(
        <div className="flex flex-wrap justify-evenly">
        {bar.map((bar,index) => (
          < Progressbar
            key={index}
            {...bar}
          />
        ))}
      </div>
    )
}

export default Progressbar