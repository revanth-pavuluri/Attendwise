import dayjs from "dayjs";
import  { createContext, useEffect, useState } from "react";
import { generateDate, months } from "./util/Cal";
import cn from "./util/Cn";
import { GrFormNext, GrFormPrevious } from "react-icons/gr";
import Timeline from "./Timeline";
import { useUserdetails } from "./UserContext";
import { get_student_dashbord } from "./services/Student";
interface ModalType{
    showModal: boolean;
    setShowModal : React.Dispatch<React.SetStateAction<boolean>>;
  } 

export const modalContext = createContext<ModalType>({} as ModalType);

export default function Calendar() {
	const [showModal,setShowModal] = useState(false);
	const days = ["S", "M", "T", "W", "T", "F", "S"];
	const {userdetails} = useUserdetails()
	const currentDate = dayjs();
	const [today, setToday] = useState(currentDate);
	const [selectDate, setSelectDate] = useState(currentDate);
	const [timelinedetails,setTimelinedetails] = useState([]);
	
    useEffect(
		() => {
			get_student_dashbord({selectDate,setTimelinedetails});
		
    },[selectDate,showModal,userdetails]);
	return (
		<div className="flex gap-10  p-12 pt-5 justify-center sm:w-3/4 mx-auto m-10 max-h items-center sm:flex-row flex-col">
			<div className="max-w-md max-h-md">
				<div className="flex justify-between items-center">
					<h1 className="select-none font-semibold text-Black">
						{months[today.month()]}, {today.year()}
					</h1>
					<div className="flex gap-10 items-center ">
						<GrFormPrevious
							className="w-5 h-5 cursor-pointer hover:scale-105 transition-all"
							onClick={() => {
								setToday(today.month(today.month() - 1));
							}}
						/>
						<h1
							className=" cursor-pointer hover:scale-105 transition-all"
							onClick={() => {
								setToday(currentDate);
							}}
						>
							Today
						</h1>
						<GrFormNext
							className="w-5 h-5 cursor-pointer hover:scale-105 transition-all"
							onClick={() => {
								setToday(today.month(today.month() + 1));
							}}
						/>
					</div>
				</div>
				<div className="grid grid-cols-7 ">
					{days.map((day, index) => {
						return (
							<h1
								key={index}
								className="text-sm text-center h-14 w-14 grid place-content-center text-gray-500 select-none"
							>
								{day}
							</h1>
						);
					})}
				</div>

				<div className=" grid grid-cols-7 ">
					{generateDate(today.month(), today.year()).map(
						({ date, currentMonth, today }, index) => {
							return (
								<div
									key={index}
									className="p-10 text-center h-14 grid place-content-center text-l border-t border-Black"
								>
									<h1
										className={cn(
											currentMonth ? "" : "text-gray-400",
											today
												? "bg-Red text-white"
												: "",
											selectDate
												.toDate()
												.toDateString() ===
												date.toDate().toDateString()
												? "bg-Black text-white"
												: "",
											"h-10 w-10 rounded-full grid place-content-center hover:bg-Black hover:text-white transition-all cursor-pointer select-none"
										)}
										onClick={() => {
											setSelectDate(date);
										}}
									>
										{date.date()}
									</h1>
								</div>
							);
						}
					)}
				</div>
			</div>
			<div className="w-full sm:px-5">
				<h1 className=" font-semibold text-Black">
					Schedule for {selectDate.toDate().toDateString()}
				</h1>
				{/* <p className="text-gray-400">No meetings for today.</p> */}
                <div >
				<modalContext.Provider value={{showModal,setShowModal}}>
                <Timeline timelineData={timelinedetails}/>
				</modalContext.Provider>
                </div>
			</div>
		</div>
	);
}