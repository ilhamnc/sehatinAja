const { nanoid } = require('nanoid');
const hash = require('bcrypt');
const Joi = require('joi');
const jwt = require('jsonwebtoken');
const db = require('../config/configdb');
const { promisify } = require('util');

const query = promisify(db.query).bind(db);

const addUsersHandler = async (request, h) => {
  try {
    const { username, email, password, confirmPassword } = request.payload;

    // Validasi input
    const schema = Joi.object({
      username: Joi.string().alphanum().min(6).max(20).required(),
      email: Joi.string().email().required(),
      password: Joi.string().min(8).required(),
      confirmPassword: Joi.string().valid(Joi.ref('password')).required(),
    });

    const { error } = schema.validate({ username, email, password, confirmPassword });

    if (error) {
      return h.response({
        error: true,
        message: error.details[0].message,
      }).code(400);
    }

    // Cek apakah username atau email sudah terdaftar
    const checkQuery = `SELECT * FROM users WHERE username='${username}' OR email='${email}'`;
    const rows = await query(checkQuery);
    const user = rows[0];

    if (user) {
      return h.response({
        error: true,
        message: 'Username atau email sudah terdaftar',
      }).code(409);
    }

    // Hash password
    const saltRounds = 10;
    const hashedPassword = await hash.hash(password, saltRounds);

    // Tambahkan user ke database
    const userId = nanoid(10);
    const insertQuery = `INSERT INTO users (id, username, email, password) VALUES ('${userId}', '${username}', '${email}', '${hashedPassword}')`;
    await query(insertQuery);

    return h.response({
      error: false,
      message: 'success',
      userId,
    }).code(201);
  } catch (error) {
    console.error(error);
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};


const getUserHandler = async (request, h) => {
  try {
    const { username } = request.params;

    // Ambil data user dari database
    const getUserQuery = `SELECT * FROM users WHERE username='${username}'`;
    const rows = await query(getUserQuery);
    const user = rows[0];

    if (!user) {
      return h.response({
        error: true,
        message: 'User tidak ditemukan',
      }).code(404);
    }

    return h.response({
      error: false,
      message: 'success',
      user,
    });
  } catch (error) {
    console.error(error);
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};


const loginHandler = async (request, h) => {
  try {
    const { username, password } = request.payload;

    // Validasi input
    const schema = Joi.object({
      username: Joi.string().alphanum().min(6).max(20).required(),
      password: Joi.string().min(8).required(),
    });

    const { error } = schema.validate({ username, password });

    if (error) {
      return h.response({
        error: true,
        message: error.details[0].message,
      }).code(400);
    }

    // Cek apakah user terdaftar
    const checkQuery = `SELECT * FROM users WHERE username='${username}'`;
    const rows = await query(checkQuery);
    const user = rows[0];

    if (!user) {
      return h.response({
        error: true,
        message: 'Username atau password salah',
      }).code(401);
    }

    // Verifikasi password
    const match = await hash.compare(password, user.password);

    if (!match) {
      return h.response({
        error: true,
        message: 'Username atau password salah',
      }).code(401);
    }

    // Generate token
    const token = jwt.sign({ userId: user.id }, 'sehatin-aja', { expiresIn: '15m' });

    return h.response({
      error: false,
      message: 'success',
      loginResult: {
        userId: user.id,
        name: user.username,
        token,
      },
    }).code(200);
  } catch (error) {
    console.error(error);
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const refreshTokenHandler = async (request, h) => {
  try {
    // Mendapatkan token dari request header
    const authHeader = request.headers.authorization;
    
    if (!authHeader || !authHeader.startsWith('Bearer')) {
      return h.response({
        error: true,
        message: 'Unauthorized',
      }).code(401);
    }

    const token = authHeader.substring(7);

    // Verifikasi token
    const secretKey = 'sehatin-aja';
    const decodedToken = jwt.verify(token, secretKey);

    // Buat JWT token baru
    const tokenPayload = { userId: decodedToken.userId };
    const options = { expiresIn: '15m' };
    const newToken = jwt.sign(tokenPayload, secretKey, options);

    return h.response({
      error: false,
      message: 'success',
      newToken,
    }).code(200);
  } catch (error) {
    console.error(error);
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const logoutHandler = async (request, h) => {
  try {
    return h.response({
      error: false,
      message: 'success',
    }).code(200);
  } catch (error) {
    console.error(error);
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

module.exports = {
  addUsersHandler,
  getUserHandler,
  loginHandler,
  refreshTokenHandler,
  logoutHandler,
};
