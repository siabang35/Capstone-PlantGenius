const { Authentication } = require('../middleware/Authentication')
const { predict } = require('../controllers/fungsiControllers')
const fungsiRoutes = [
  {
    path: '/fungsi',
    method: 'POST',
    handler: predict,
    options: {
      pre: [Authentication]
    }
  }
]

module.exports = { fungsiRoutes }
