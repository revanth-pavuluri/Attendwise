import  { useEffect, useState } from 'react';
import { useForm, SubmitHandler, Controller } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { FinalFacDashProp } from './FDashboard';
import { useUserdetails } from './UserContext';
import { attend_add,attend_edit } from './services/Attendance';
import { get_all_subjects } from './services/Subject';


interface AddEditProps {
    initialData ?: FinalFacDashProp;
    handle : ()=> void;
 }

type subType = {
  id : number,
  code : string,
  name : string,
}

export type subTypes = subType[];

const AttendAddEdit = ({initialData, handle} : AddEditProps) => {
const [subdetails,SetSubdetails] = useState<subTypes>([])
  const {userdetails} = useUserdetails();
  useEffect(
    () => {
      get_all_subjects(SetSubdetails)
    
  },[]);
  const schema = yup.object().shape({
    subjectname: yup.string().default(initialData?.subjectname ? initialData?.subjectname : ""),
    periods: yup.number().required('periods is required'),
    status: yup.number().default(0),
    type : yup.string().required('Type is required'),
    facultyname : yup.string().default(userdetails.name),
    id: yup.number().default( initialData?.id ? initialData?.id: 0),                    
    facultyid: yup.number().default(initialData?.facultyid ? initialData?.facultyid: userdetails.id),                 
    subjectid: yup.number().default(initialData?.subjectid ? initialData?.subjectid: 0),                      
    subjectcode: yup.string().default(initialData?.subjectcode ? initialData?.subjectcode : ""),                        
    data: yup.string().default(initialData?.data? initialData?.data :""),        
    classname:  yup.string().required('Class name is required')
    .matches(
      /^(P1|P2|E1|E2|E3|E4)_(CSE|ECE|MECH|CE|EEE)-\d{2}$/,
      'Invalid class name format. Example format: P1_CSE-01'
    ),        
    date: yup.string().default(initialData?.date ? initialData?.date :""),        
    createdon: yup.string().default(initialData?.createdon ? initialData?.createdon:""),        
    updatedon: yup.string().default(initialData?.updatedon ? initialData?.updatedon :"")
  });
  
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FinalFacDashProp>({
    defaultValues : initialData || {},
    resolver: yupResolver(schema),
  });

  const onSubmit: SubmitHandler<FinalFacDashProp> = async(data) => {
    console.log(data)
    if(initialData){
      attend_edit({data,handle})
    }
    else{
      attend_add({data, handle})
    }
    
  };


  return (
    <div className="flex  justify-center items-center  bg-gray-100">
      <form
        className="bg-white rounded-lg shadow-md p-6 max-w-md w-full"
        onSubmit={handleSubmit(onSubmit)}
      >
        <div className="mb-4">
  <label htmlFor="subjectid" className="block text-sm font-medium text-gray-700">
    Subject name
  </label>
  <Controller
    name="subjectid"
    control={control}
    render={({ field }) => (
      <select
        id="subjectname"
        {...field}
        className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
      >
        <option value="">Select a subject</option>
        {subdetails.map((subject) => (
          <option key={subject.id} value={subject.id}>
            {subject.name}
          </option>
        ))}
      </select>
    )}
  />
  <p className="text-red-500 text-xs mt-1">{errors.subjectid?.message}</p>
</div>
        <div className="mb-4">
          <label htmlFor="periods" className="block text-sm font-medium text-gray-700">
            Periods
          </label>
          <Controller
            name="periods"
            control={control}
            render={({ field }) => (
              <input
                type="number"
                id="periods"
                placeholder="No. of periods"
                {...field}
                className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
              />
            )}
          />
          <p className="text-red-500 text-xs mt-1">{errors.periods?.message}</p>
        </div>
        <div className="mb-4">
          <label htmlFor="type" className="block text-sm font-medium text-gray-700">
            Type
          </label>
          <Controller
            name="type"
            control={control}
            render={({ field }) => (
              <input
                type="text"
                id="type"
                placeholder="OTP or QR"
                {...field}
                className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
              />
            )}
          />
          <p className="text-red-500 text-xs mt-1">{errors.type?.message}</p>
        </div>
        <div className="mb-4">
  <label htmlFor="date" className="block text-sm font-medium text-gray-700">
    Date
  </label>
  <Controller
    name="date"
    control={control}
    render={({ field }) => (
      <input
        type="date"
        id="date"
        {...field}
        className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
      />
    )}
  />
  <p className="text-red-500 text-xs mt-1">{errors.date?.message}</p>
</div>
        <div className="mb-4">
          <label htmlFor="classname" className="block text-sm font-medium text-gray-700">
            Class name
          </label>
          <Controller
            name="classname"
            control={control}
            render={({ field }) => (
              <input
                type="text"
                id="classname"
                placeholder="class"
                {...field}
                className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
              />
            )}
          />
          <p className="text-red-500 text-xs mt-1">{errors.classname?.message}</p>
        </div>
        <button
          type="submit"
          className="bg-Aqua-300 ml-3 text-Black py-2 px-4 rounded-md hover:bg-Aqua"
        >
          Save
        </button>
      </form>
    </div>
    
  );
};

export default AttendAddEdit;