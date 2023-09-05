/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"], 
  theme: {
   
    extend: {
      colors: {
        Red: {
          100 : 'rgba(250, 72, 75, 0.1)',
          200 : 'rgba(250, 72, 75, 0.2)',
          300 : 'rgba(250, 72, 75, 0.5)',
          400 : 'rgba(250, 72, 75, 0.7)',
          500 : 'rgba(250, 72, 75, 1)',
          DEFAULT: '#fa484b',
        },
        Aqua: {
          100: 'rgba(68, 214, 194, 0.1)',
          200: 'rgba(68, 214, 194, 0.2)',
          300: 'rgba(68, 214, 194, 0.5)',
          400 : 'rgba(68, 214, 194, 0.7)',
          500 : 'rgba(68, 214, 194, 1)',
          DEFAULT: '#44d6c2', 
        },
        Yellow: {
          100: 'rgba(250, 195, 61, 0.1)',
          200: 'rgba(250, 195, 61, 0.2)',
          300: 'rgba(250, 195, 61, 0.5)',
          400 : 'rgba(250, 195, 61, 0.7)',
          500 : 'rgba(250, 195, 61, 1)',
          DEFAULT: '#fac33d', 
        },
        Black : {
          100 : 'rgba(50,77,91,0.7)',
          DEFAULT : '#324d5b',
        }
      },
    },
  },
  plugins: [],
}

