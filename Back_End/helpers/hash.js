const bcrypt = require('bcrypt')

function hash (input) {
  return bcrypt.hashSync(input, 10)
}

function compare (input, hashed) {
  return bcrypt.compareSync(input, hashed)
}

module.exports = {
  hash, compare
}
