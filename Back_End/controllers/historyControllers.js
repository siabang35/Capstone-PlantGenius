const Boom = require('@hapi/boom')
const { History } = require('../models')
const { penanganan } = require('../models')
const { Op } = require('sequelize')

module.exports = {
  createHistory: async (userId, result, imageUrl) => {
    await History.create({
      userId, result, imageUrl
    })
  },

  listHistory: async (request, h) => {
    try {
      const userId = request.auth.credentials.id
      const history = await History.findAll({
        where: {
          userId
        },
        attributes: ['id', 'result', 'imageUrl', 'createdAt']
      })
      console.log(history.dataValues)
      return h.response({
        history
      }).code(200)
    } catch (error) {
      throw Boom.badRequest(error)
    }
  },

  getHistoryById: async (request, h) => {
    try {
      const userId = request.auth.credentials.id
      const historyId = request.params.id
      const history = await History.findOne({
        where: {
          userId,
          id: historyId
        },
        attributes: ['id', 'result', 'imageUrl']
      })
      const penyakit = await penanganan.findOne({
        where: {
          penyakit: history.dataValues.result
        },
        attributes: ['penanganan']
      })
      return h.response({
        id: history.dataValues.id,
        penyakit: history.dataValues.result,
        penanganan: penyakit.dataValues.penanganan,
        imageUrl: history.dataValues.imageUrl
      })
    } catch (error) {
      throw Boom.notFound('History tidak ditemukan !')
    }
  },

  deleteHistory: async (request, h) => {
    try {
      const { historyIds } = request.payload
      await History.destroy({
        where: {
          id: {
            [Op.in]: historyIds
          }
        }
      })
      return h.response({
        message: 'History berhasil dihapus !'
      })
    } catch (error) {
      throw Boom.badRequest({ error })
    }
  }
}
