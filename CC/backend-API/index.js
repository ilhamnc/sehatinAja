// Mengimpor modul
const mysql = require('mysql')

// Membuat koneksi database
const connection = mysql.createConnection({
	host: '34.101.253.45',
	user: 'root',
	password: 'authentic1009',
	database: 'sehatin-db',
	port: '3306',
	timeout: 60000 // Timeout dalam milidetik (60 detik)
})

// Menghubungkan ke database
connection.connect(function (err) {
	if (err) {
		console.log("Terjadi kesalahan dalam koneksi")
		console.log(err)
	}
	else {
		const { user } = "ncd20sd9";
		console.log(`Database Terhubung`)
		connection.query('SELECT * FROM users WHERE username = "ncdsg209"',
			function (err, result) {
				if (err)
					console.log(`Terjadi kesalahan dalam mengeksekusi query - ${err}`)
				else
					console.log("Hasil: ", result)
			})
	}
})
