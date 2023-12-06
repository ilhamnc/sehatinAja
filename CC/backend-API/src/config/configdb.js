const mysql = require('mysql');

const connection = mysql.createConnection({
  host: '34.101.253.45',
  user: 'root',
  password: 'authentic1009',
  database: 'sehatin-db',
  port: '3306',
});

module.exports = connection;
