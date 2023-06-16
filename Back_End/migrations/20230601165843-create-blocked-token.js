'use strict'
/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up (queryInterface, Sequelize) {
    await queryInterface.createTable('blockedTokens', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      token: {
        type: Sequelize.STRING
      },
      expiredAt: {
        type: Sequelize.DATE
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE
      }
    })
    await queryInterface.sequelize.query(`
    CREATE EVENT delete_expired_tokens
    ON SCHEDULE EVERY 1 MINUTE
    STARTS CURRENT_TIMESTAMP
    DO
      DELETE FROM blockedTokens WHERE expiredAt < NOW();
    `)
  },
  async down (queryInterface, Sequelize) {
    await queryInterface.dropTable('blockedTokens')
    await queryInterface.sequelize.query(`
    DROP EVENT IF EXISTS delete_expired_tokens;
  `)
  }
}
