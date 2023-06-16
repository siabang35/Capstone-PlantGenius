'use strict'
const {
  Model
} = require('sequelize')
module.exports = (sequelize, DataTypes) => {
  class History extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate (models) {
      // define association here
    }
  }
  History.init({
    result: DataTypes.STRING,
    userId: DataTypes.INTEGER,
    imageUrl: DataTypes.TEXT
  }, {
    sequelize,
    modelName: 'History'
  })
  // Hapus history lama ketika user mencapai batas 50 history
  History.addHook('beforeCreate', async (history) => {
    const maxHistory = 5
    const historyCount = await History.count({
      where: {
        userId: history.userId
      }
    })
    if (historyCount >= maxHistory) {
      await History.destroy({
        where: {
          userId: history.userId
        },
        limit: 1,
        order: [['createdAt', 'ASC']]
      })
    }
  })
  return History
}
