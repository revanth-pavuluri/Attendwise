import React, {useContext, useEffect, useState } from "react";
// import QrReader from "react-qr-reader";
import { Html5QrcodeScanner } from "html5-qrcode";
import ModalBody from "./ModalBody";
import axios from "axios";
import { useUserdetails } from "./UserContext";
import { toast } from "react-toastify";
import { modalContext } from "./Calendar";

const QrModal  = ({id} : {id : number}) => {
  const {showModal, setShowModal} = useContext(modalContext);
  const [scanResultWebCam, setScanResultWebCam] =  useState('');
  const {userdetails} = useUserdetails();
  
    useEffect(() => {
      let scanner : any = null; // Declare the scanner variable
    
      if (showModal) {
        scanner = new Html5QrcodeScanner('reader', {
          qrbox: {
            width: 250,
            height: 250,
          },
          fps: 5,
        }, false);
    
        const success = (result : any) => {
          setScanResultWebCam(result);
          
          if(result.split("@")[0] == id){
            const parsedTimestamp = parseInt(result.split("@")[1]);     
             const currentTime = Date.now();
              if(currentTime-parsedTimestamp <= 5000){
                (async () =>{
                  await axios.post(`/attendance/mark/${id}/${userdetails.id}`)
                     .then((response) => {
                       if (response.status === 200){
                          setShowModal(false)  
                          toast.success ("Attendance marked successfully!")   
                      
                       }
                     })
                     .catch((error) => {
                      if (error.status === 401 || error.status === 403 || error.status === 400) {
                        toast.error("Error occured, Try again!!!");
                      } else if(error.status === 404 || error.status === 504 || error.status === 423 ) {
                        toast.error(error);
                      }
                       
                     
                     })
                 })()
                 if (scanner) {
                  scanner.clear(); // Stop the scanning process
                }
              }else{
                toast.warning(`Qr code expired ! Try again`)
              }
          }
          else{
            toast.error("Invalid QR!!!")
          }
          
        };
    
        const handleErrorWebCam = (error : any) => {
          console.log(error);
        };
    
        scanner.render(success, handleErrorWebCam);
      }
    
      // Cleanup function
      return () => {
        if (scanner) {
          scanner.clear(); // Clear the scanner when the component unmounts
        }
      };
    }, [showModal]);
        
  
  
  return (
   <ModalBody 
   modalbuttontext="Mark"
   modalbuttonhandle={()=>setShowModal(true)}
   modal={showModal}
   titlename="scanner"
   body = {<div className="h-96 w-96 " id = 'reader'></div>}
   closehandle={()=>setShowModal(false)}
   />
  );
}

export default QrModal