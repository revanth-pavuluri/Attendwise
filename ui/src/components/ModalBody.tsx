import { ReactNode } from "react";
import Button from "./Button";
import Modal from "./Modal";

type modalprop = {
    modalbuttontext : string;
    modalbuttonhandle : () => void;
    modal : boolean;
    titlename : string;
    body : ReactNode;
    closehandle : () => void;
}
    
const ModalBody = ({modalbuttontext,
    modalbuttonhandle,
    modal,
    titlename,
    body,
    closehandle,
   } : modalprop) => {
  return (
    <>
      <Button text={modalbuttontext} handle={modalbuttonhandle}/>
      {modal ? (
         <>
         <Modal 
         header = {{
          title : titlename,
          closehandle : closehandle,
         }}
         body = {body}/>
       </> 
      ) : null}
    </>
  )
}

export default ModalBody;