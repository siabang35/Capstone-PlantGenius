const { Storage } = require('@google-cloud/storage')

// Inisialisasi Google Cloud Storage
const storage = new Storage({
  projectId: 'plant-genius-bucket',
  keyFilename: './gcpKey/deteksi-penyakit-tanaman-e6ba066c97d7.json'
})
// Inisialisasi nama bucket
const bucketName = 'plant-genius-bucket'

async function uploadGCS (localImagePath, fileName) {
  try {
    await storage.bucket(bucketName).upload(localImagePath, {
      destination: fileName,
      public: true
    })

    const [file] = await storage.bucket(bucketName).file(fileName).getSignedUrl({
      action: 'read',
      expires: '12-31-9999'
    })

    return file
  } catch (error) {
    console.error('Terjadi kesalahan saat mengunggah gambar', error.message)
    throw error
  }
}

exports.uploadGCS = uploadGCS
