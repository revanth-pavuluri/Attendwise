import React, { useEffect } from 'react';
import { useForm, SubmitHandler, Controller } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { FinalFacStdProp } from './StdTable';
import axios from 'axios';
import { toast } from 'react-toastify';
import { student_add, student_edit } from './services/Student';


interface StdTableAddEditProps {
    initialData ?: FinalFacStdProp;
    handle : ()=> void;
    // Optional initial data for autofill
  }




const StdTableAddEdit = ({initialData,handle} : StdTableAddEditProps) => {


  const schema = yup.object().shape({
    id : yup.number().default(initialData ? initialData.id : 0),
    username: yup.string().required('Student ID is required')
    .matches(/^[NS]\d{6}$/, 'Student ID should start with N or S followed by six digits'),
    name: yup.string().required('Name is required'),
    rollnumber: yup.number().required('Roll number is required'),
    classname : yup.string().required('Class name is required')
    .matches(
      /^(P1|P2|E1|E2|E3|E4)_(CSE|ECE|MECH|CE|EEE)-\d{2}$/,
      'Invalid class name format. Example format: P1_CSE-01'
    ),
  });


  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FinalFacStdProp>({
    defaultValues : initialData || {},
    resolver: yupResolver(schema),
  });

  const onSubmit: SubmitHandler<FinalFacStdProp> = async(data) => {
    if(initialData){
      student_edit({data,handle})
    }else{
      student_add({data,handle})
    }
    
  };


  return (
    <div className="flex  justify-center items-center  bg-gray-100">
      <form
        className="bg-white rounded-lg shadow-md p-6 max-w-md w-full"
        onSubmit={handleSubmit(onSubmit)}
      >
        <div className="mb-4">
          <label htmlFor="username" className="block text-sm font-medium text-gray-700">
            Student ID
          </label>
          <Controller
            name="username"
            control={control}
            render={({ field }) => (
              <input
                type="text"
                id="username"
                placeholder="Student ID"
                {...field}
                className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
              />
            )}
          />
          <p className="text-red-500 text-xs mt-1">{errors.username?.message}</p>
        </div>
        <div className="mb-4">
          <label htmlFor="name" className="block text-sm font-medium text-gray-700">
            Name
          </label>
          <Controller
            name="name"
            control={control}
            render={({ field }) => (
              <input
                type="text"
                id="name"
                placeholder="Student name"
                {...field}
                className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
              />
            )}
          />
          <p className="text-red-500 text-xs mt-1">{errors.name?.message}</p>
        </div>
        <div className="mb-4">
          <label htmlFor="rollnumber" className="block text-sm font-medium text-gray-700">
            Roll No.
          </label>
          <Controller
            name="rollnumber"
            control={control}
            render={({ field }) => (
              <input
                type="number"
                id="rollnumber"
                placeholder=" Ex:1"
                {...field}
                className="mt-1 p-2 w-full border rounded-md focus:ring focus:ring-blue-200"
              />
            )}
          />
          <p className="text-red-500 text-xs mt-1">{errors.rollnumber?.message}</p>
        </div>
        <div className="mb-4">
          <label htmlFor="classname" className="block text-sm font-medium text-gray-700">
            Class
          </label>
          <Controller
            name="classname"
            control={control}
            render={({ field }) => (
              <input
                type="text"
                id="classname"
                placeholder="YEAR_BRANCH-section"
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

export default StdTableAddEdit;