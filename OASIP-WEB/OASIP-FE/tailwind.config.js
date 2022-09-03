module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {
      fontFamily: {
        itim : 'Itim',
        rubik : 'Rubik'
      },
    },
  },
  plugins: [require('flowbite/plugin')],
}
