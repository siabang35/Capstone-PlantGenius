const Boom = require('@hapi/boom')
const { createHistory } = require('./historyControllers')
const sharp = require('sharp')
const fs = require('fs')
const path = require('path')
const { penanganan } = require('../models')
const { uploadGCS } = require('../helpers/cloudStorage')
let imagePath
module.exports = {
  predict: async (request, h) => {
    try {
      // Mendapatkan userId dan userEmail
      const userId = request.auth.credentials.id
      // Mendapatkan imageBase64
      const { imageBase64 } = request.payload
      // Konversi base64 ke Buffer
      const imageData = Buffer.from(imageBase64, 'base64')
      // Konversi gambar menggunakan sharp
      const convertedImageBuffer = await sharp(imageData)
        .toFormat('jpg')
        .toBuffer()
      // Membuat unik number
      const imageNumber = Math.floor(Math.random() * 1000) + 1
      // Membuat fileName
      const fileName = `${userId}_${imageNumber}.jpg`
      // Menyimpan gambar ke direktori lokal
      const tempDir = path.join(__dirname, 'temp')
      if (!fs.existsSync(tempDir)) {
        fs.mkdirSync(tempDir)
      }
      imagePath = path.join(tempDir, fileName)
      fs.writeFileSync(imagePath, convertedImageBuffer)

      // Lakukan permintaan HTTP POST ke localhost:5000/predict
      const apiUrl = 'http://localhost:5000/predict'
      const requestData = {
        imagePath
      }
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      })

      const responseData = await response.json()
      // Mendapatkan URL gambar
      const imageUrl = await uploadGCS(imagePath, fileName).then(imageUrl => {
        return imageUrl
      }).catch(error => {
        throw Boom.badGateway(error)
      })
      // Membuat history
      createHistory(userId, responseData.prediction, imageUrl)
      // Penanganan
      const hasil = await penanganan.findOne({
        where: {
          penyakit: responseData.prediction
        },
        attributes: ['penyakit', 'penanganan']
      })
      // Menghapus gambar sementara
      fs.unlinkSync(imagePath)
      return h.response({
        penyakit: hasil.penyakit,
        penanganan: hasil.penanganan,
        imageUrl
      })
    } catch (error) {
      fs.unlinkSync(imagePath)
      throw Boom.badData()
    }
  }
}
