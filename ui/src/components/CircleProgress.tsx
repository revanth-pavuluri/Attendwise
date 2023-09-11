import { useState } from 'react';
type percentprop = {
    percent : number;
}
const CircleProgress = ({percent} : percentprop) => {
  const circumference = 2 * (22 / 7) * 90;
  const dashOffset = circumference - (percent / 100) * circumference;
  console.log("dash"+dashOffset)
  let color = 'text-Red';
  if(percent < 86 && percent > 74){
    color ='text-Yellow';
  }
  else if(percent > 85){
    color = 'text-Aqua';
  }
  return (
    <div className="flex items-center justify-center">
      <svg className="transform -rotate-90 w-72 h-72">
        <circle cx="145" cy="145" r="90" stroke="currentColor" strokeWidth="20" fill="transparent" className="text-gray-300" />
        <circle
          cx="145"
          cy="145"
          r="90"
          stroke="currentColor"
          strokeWidth="20"
          fill="transparent"
          strokeDasharray={circumference}
          strokeDashoffset={dashOffset}
          className={color}
        />
      </svg>
      <span className="absolute text-5xl text-Black">{`${percent}%`}</span>
    </div>
  );
};

export default CircleProgress;
