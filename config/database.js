const mogoose = require('mongoose')

const { DB_CON_STRING } = process.env

module.exports = () => {
    // mogoose.connect("mongodb://localhost/ecommerce")
    // mogoose.connect("mongodb+srv://native_ecommer:native_ecommer@cluster0.bktldz9.mongodb.net/?retryWrites=true&w=majority")
     mogoose.connect("mongodb://127.0.0.1:27017/ecommerce")
        .then(() => console.log('DB Connection Successfull'))
        .catch(err => console.log(err.message))
}