import { Link} from "react-router-dom";



const PageNotFound = () => {
  return (
    <>
    <div className="flex items-center justify-center w-screen h-screen">
      <div className="px-4 lg:py-12">
        <div className="lg:gap-4 lg:flex">
          <div className="flex flex-col items-center justify-center md:py-24 lg:py-32">
            <h1 className="font-bold text-Red text-9xl">404</h1>
            <p className="mb-2 text-2xl font-bold text-center text-Black md:text-3xl">
              <span className="text-Red">Oops!</span> Page not found!
            </p>
            <p className="mb-8 text-center text-Black md:text-lg">
              The page you’re looking for doesn’t exist.
            </p>
            <Link
              to="/"
              className="px-5 py-2 rounded-md text-Black bg-Aqua hover:bg-Aqua-400"
            >
              Go home
            </Link>
          </div>
          <div className="mt-4">
            <img
              src="https://cdn.pixabay.com/photo/2016/11/22/23/13/black-dog-1851106__340.jpg"
              alt="img"
              className="object-cover w-full h-full"
            />
          </div>
        </div>
      </div>
    </div>
  </>
  )
}

export default PageNotFound