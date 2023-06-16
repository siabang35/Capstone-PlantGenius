const { User } = require('../models')
const { blockedToken } = require('../models')
const { hash, compare } = require('../helpers/hash')
const { sign } = require('../helpers/jwt')
const { validatePassword, validateEmail, validateName } = require('../helpers/validate')
module.exports = {
  signUp: async (request, h) => {
    try {
      let { name, email, password } = request.payload
      email = email.toLowerCase()
      const errors = new Map() // Menyimpan kesalahan yang terjadi menggunakan Map()
      validateEmail(email, errors)
      validateName(name, errors)
      validatePassword(password, errors)
      await User.findOne({
        where: {
          email
        }
      }).then(user => {
        if (user) {
          errors.set('email', { message: 'Email sudah terdaftar' })
        }
      })

      if (errors.size > 0) {
        const errorDetails = Object.fromEntries(errors)
        return h.response({
          message: 'Validation Error',
          details: errorDetails
        }).code(400)
      }

      password = hash(password)

      const createUser = await User.create({ name, email, password })
      return h.response({
        User: createUser
      }).code(201)
    } catch (error) {
      return h.response({
        message: error.message
      }).code(400)
    }
  },

  login: async (request, h) => {
    try {
      let { password, email } = request.payload
      let verifyUser
      if (email) {
        email = email.toLowerCase()
        verifyUser = await User.findOne({
          where: {
            email
          },
          attributes: ['id', 'email', 'password', 'name']
        })

        if (!verifyUser) {
          return h.response({
            message: 'Email tidak terdaftar'
          }).code(400)
        }
      } else {
        return h.response({
          message: 'Email diperlukan'
        }).code(400)
      }

      if (!compare(password, verifyUser.password)) {
        return h.response({
          message: 'Password salah!'
        }).code(400)
      }

      const token = await sign({
        id: verifyUser.id,
        email: verifyUser.email,
        no_telepon: verifyUser.no_telepon
      })

      return h.response({
        token,
        email,
        name: verifyUser.name
      })
    } catch (error) {
      return h.response({
        message: error.message
      }).code(500)
    }
  },

  logout: async (request, h) => {
    try {
      const token = request.headers.authorization
      if (!token) {
        return h.response({
          message: 'Token diperlukan!'
        }).code(403)
      }

      /* `const expiredAt = new Date(request.auth.credentials.exp * 1000)` mengonversi masa berlaku
       waktu token JWT (yang dalam format stempel waktu Unix) ke objek Tanggal JavaScript.
       `request.auth.credentials.exp` adalah waktu kedaluwarsa token JWT di stempel waktu Unix
       format, yaitu jumlah detik sejak 1 Januari 1970, 00:00:00 UTC. Mengalikannya dengan
       1000 mengubahnya menjadi milidetik, yang merupakan format yang diperlukan oleh konstruktor `Date`. Itu
       variabel `expiredAt` yang dihasilkan adalah objek Tanggal JavaScript yang mewakili waktu kedaluwarsa
       token JWT. */
      const expiredAt = new Date(request.auth.credentials.exp * 1000)
      await blockedToken.create({ token, expiredAt })

      return h.response({
        message: 'Logout Berhasil'
      }).code(200)
    } catch (error) {
      return h.response({
        message: error.message
      }).code(500)
    }
  }
}
