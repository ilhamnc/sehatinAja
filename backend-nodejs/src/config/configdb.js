const mysql = require('mysql');

const connection = mysql.createConnection({
  host: '34.101.103.107',
  user: 'root',
  password: 'sehatin1009',
  database: 'sehatin-db',
  port: '3306',
});

module.exports = connection;
