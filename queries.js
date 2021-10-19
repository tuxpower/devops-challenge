const Pool = require('pg').Pool
const pool = new Pool({
  user: 'devops',
  host: 'localhost',
  database: 'devops_test',
  password: 'password',
  port: 5432,
})

const getUserByUsername = async (request, response) => {
  const username = request.params.username

  pool.query('SELECT date_of_birth FROM users WHERE username = $1', [username], (error, results) => {
    if (error) {
      throw error
    }
    response.status(200).json(results.rows[0].date_of_birth)
  })
}

const createUser = (request, response) => {
  const username = request.params.username
  const { dateOfBirth } = request.body

  pool.query('INSERT INTO users (username, date_of_birth) VALUES ($1, $2)', [username, dateOfBirth], (error, results) => {
    if (error) {
      throw error
    }
    response.status(204).json()
  })
}

module.exports = {
  getUserByUsername,
  createUser,
}