const Pool = require('pg').Pool
const pool = new Pool({
  user: 'devops',
  host: 'localhost',
  database: 'devops_test',
  password: 'password',
  port: 5432,
})

const getDateOfBirthByUsername = async (req, res, next) => {
  const username = req.params.username

  pool.query('SELECT date_of_birth FROM users WHERE username = $1', [username], (error, results) => {
    if (error) {
      throw error
    }
    res.send(200, results.rows[0].date_of_birth);
    return next();
  })
}

// curl -X PUT 'http://localhost:8080/hello/jose' -d '{"dateOfBirth":"1975-08-10"}' -H 'Content-Type: application/json'
const createUser = (req, res, next) => {
  const username = req.params.username
  const dateOfBirth  = req.body.dateOfBirth

  pool.query('INSERT INTO users (username, date_of_birth) VALUES ($1, $2)', [username, dateOfBirth], (error, results) => {
    if (error) {
      throw error
    }
    res.send(204);
    return next();
  })
}

module.exports = {
  getDateOfBirthByUsername,
  createUser,
}