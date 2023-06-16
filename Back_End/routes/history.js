const { Authentication } = require('../middleware/Authentication')
const { listHistory, deleteHistory, getHistoryById } = require('../controllers/historyControllers')
const historyRoutes = [
  {
    path: '/getHistory/{id}',
    method: 'get',
    handler: getHistoryById,
    options: {
      pre: [Authentication]
    }
  },
  {
    path: '/getHistory',
    method: 'get',
    handler: listHistory,
    options: {
      pre: [Authentication]
    }

  },
  {
    path: '/deleteHistory',
    method: 'post',
    handler: deleteHistory,
    options: {
      pre: [Authentication]
    }
  }
]

module.exports = {
  historyRoutes
}
