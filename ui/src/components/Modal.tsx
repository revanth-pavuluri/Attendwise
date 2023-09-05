import { MdClose } from "react-icons/md"
import Button, { buttonprop } from "./Button"
import { ReactNode } from "react"

type modalProp = {
    header ?: {
        title : string,
        closehandle : ()=>void,
    },
    footer ?: {
        button : buttonprop,
    },
    body : ReactNode,
}

const Modal = ({header,footer,body} : modalProp) => {
  return (
    <>
    <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
    <div className="fixed flex justify-center items-center top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div className="relative  mx-auto content-center w-full max-w-lg max-h-full">
      {/* Modal content */}
      <div className="relative bg-white rounded-lg shadow ">
        {/* Modal header */}
        {header && 
        <div className="flex items-center justify-between p-5 border-b rounded-t ">
        <h3 className="text-xl font-medium text-gray-900 ">
          {header.title}
        </h3>
        <button
          type="button"
          className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ml-auto inline-flex justify-center items-center "
          onClick={header.closehandle}>
          <span><MdClose className="h-8 w-8 text-Red" onClick={header.closehandle}/></span>
        </button>
      </div>}
        
        {/* Modal body */}
        <div className="p-6 space-y-6">
         {body}
        </div>
        {/* Modal footer */}
        {footer && 
        <div className="flex items-center justify-center p-6 space-x-2 border-t border-gray-200 rounded-b ">
        <Button text={footer.button.text} handle={footer.button.handle}/>
      </div>}
        
      </div>
    </div>
  </div>
  </>
  )
}

export default Modal