import  { useEffect, useState } from "react";
import QRCode from 'qrcode';
import ModalBody from "./ModalBody";

const QrGenModal = ({aid} : {aid : number}) => {
    const [showModal, setShowModal] = useState(false);
  
  const [imageUrl, setImageUrl] = useState('');
  const [text, setText] = useState('');
  const generate = async() => {
    try {
      console.log(Date.now().toString());
      setText(Date.now().toString());
      const response = await QRCode.toDataURL((aid+"@"+Date.now().toString()).toString());
      setImageUrl(response);
    } catch (error) {
      console.log(error);
    }
  }
  const firstgen = () => {
    setShowModal(true);
    generate();
  }

  useEffect(() => {
    if (showModal) {
       // Update text with current timestamp
      // Generate initial QR code and start interval
      
      const setid = setInterval(()=> {
        generate()
        }, 5000);
      
    return () => {
      clearInterval(setid);
    };
} else {
    // If showModal is false, clear the interval and reset state
    setText('');
    setImageUrl('');
  }
  }, [showModal]);

  return (
    <ModalBody 
    modalbuttontext="QR"
    modalbuttonhandle={firstgen}
    modal={showModal}
    titlename="Generator"
    body = {imageUrl ? (<a href={imageUrl} download><img src={imageUrl} className="h-96 w-96" alt="img"/></a>) : <p className="text-Black font-l">Generating...</p>}
    closehandle={()=>setShowModal(false)}
   
    />
  );
}
export default QrGenModal