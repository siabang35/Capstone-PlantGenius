const { verify } = require('../helpers/jwt')
const Models = require('../models')
const Boom = require('@hapi/boom')

module.exports = {
  Authentication: async (request, h) => {
    try {
      const token = request.headers.authorization // Mendapatkan token dari headers
      if (!token) {
        throw Boom.unauthorized('Token tidak ditemukan')
      }
      const nonBlockedToken = await Models.blockedToken.findOne({
        where: {
          token
        }
      })
      if (nonBlockedToken) {
        throw Boom.unauthorized('Harap login kembali')
      }

      const decodedToken = verify(token) // verify token
      const { id, email, exp } = decodedToken

      const user = await Models.User.findOne({ where: { id, email } }) // Mengambil data pengguna berdasarkan id
      if (!user) {
        throw Boom.unauthorized('Pengguna tidak ditemukan')
      }
      // Menyimpan informasi pengguna dalam atribut `request.auth.credentials`
      request.auth.credentials = {
        id: user.id,
        email: user.email,
        exp
      }
      console.log(decodedToken)
      return h.continue
    } catch (err) {
      throw Boom.unauthorized(err.message)
    }
  }
}
