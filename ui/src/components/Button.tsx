export type buttonprop = {
    text : string;
    handle : ()=>void;
}

const Button=({text,handle} : buttonprop) =>{
  return (
    <button
        className="bg-Aqua m-1 text-Black active:bg-Aqua-300 font-bold uppercase  mx-auto text-l px-3 py-2 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
        type="button"
        onClick={handle}
      >
    {text}
    </button>
  )
}

export default Button