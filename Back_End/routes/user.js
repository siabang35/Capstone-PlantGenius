const userController = require('../controllers/userControllers')
const { Authentication } = require('../middleware/Authentication')
const userRoutes = [
  {
    path: '/register',
    method: 'POST',
    handler: userController.signUp
  },
  {
    path: '/login',
    method: 'POST',
    handler: userController.login
  },
  {
    path: '/logout',
    method: 'POST',
    handler: userController.logout,
    options: {
      pre: [Authentication]
    }
  }
]

module.exports = { userRoutes }
