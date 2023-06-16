'use strict'

const Hapi = require('@hapi/hapi')
const { userRoutes } = require('./routes/user')
const { fungsiRoutes } = require('./routes/fungsi')
const { historyRoutes } = require('./routes/history')

const server = Hapi.server({
  port: 3000,
  host: '0.0.0.0'
})

async function startServer () {
  await server.start()
  console.log('Server running on', server.info.uri)
}

server.route(userRoutes)
server.route(fungsiRoutes)
server.route(historyRoutes)

startServer().catch((err) => {
  console.error('Error starting server:', err)
})
