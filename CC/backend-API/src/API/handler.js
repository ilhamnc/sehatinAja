const { nanoid } = require('nanoid');
const hash = require('bcrypt');
const Joi = require('joi');
const jwt = require('jsonwebtoken');
const { promisify } = require('util');
const db = require('../config/configdb');

const query = promisify(db.query).bind(db);

const addUsersHandler = async (request, h) => {
  try {
    const {
      username, email, password, confirmPassword,
    } = request.payload;

    // Validasi input
    const schema = Joi.object({
      username: Joi.string().alphanum().min(6).max(20)
        .required(),
      email: Joi.string().email().required(),
      password: Joi.string().min(8).required(),
      confirmPassword: Joi.string().valid(Joi.ref('password')).required(),
    });

    const { error } = schema.validate({
      username, email, password, confirmPassword,
    });

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
        message: 'Username or email has been used',
      }).code(409);
    }

    // Hash password
    const saltRounds = 10;
    const hashedPassword = await hash.hash(password, saltRounds);

    // Tambahkan user ke database
    const userId = nanoid(10);
    const insertUser = `INSERT INTO users (id, username, email, password) VALUES ('${userId}', '${username}', '${email}', '${hashedPassword}')`;
    await query(insertUser);

    return h.response({
      error: false,
      message: 'Success',
      userId,
    }).code(201);
  } catch (error) {
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const getUserHandler = async (request, h) => {
  try {
    // Mendapatkan refreshToken dari cookies
    const { refreshToken } = request.state;

    if (refreshToken) {
      // Ambil informasi user dari refreshToken
      const decodedToken = jwt.verify(refreshToken, 'sehatin-aja');
      const { userId } = decodedToken;

      // Ambil data user dari database
      const getUser = `SELECT * FROM users WHERE id='${userId}'`;
      const rows = await query(getUser);
      const user = rows[0];

      if (!user) {
        return h.response({
          error: true,
          message: 'User not found',
        }).code(404);
      }

      return h.response({
        error: false,
        message: 'Success',
        user,
      });
    }
    return h.response({
      error: true,
      message: 'Token not found',
    }).code(401);
  } catch (error) {
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
      username: Joi.string().alphanum().min(6).max(20)
        .required(),
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
    const checkUsername = `SELECT * FROM users WHERE username='${username}'`;
    const rows = await query(checkUsername);
    const user = rows[0];

    if (!user) {
      return h.response({
        error: true,
        message: 'Incorrect username or password',
      }).code(401);
    }

    // Verifikasi password
    const match = await hash.compare(password, user.password);

    if (!match) {
      return h.response({
        error: true,
        message: 'Incorrect username or password',
      }).code(401);
    }

    // Generate token
    const token = jwt.sign({ userId: user.id }, 'sehatin-aja', { expiresIn: '15m' });

    // Simpan refreshToken dalam cookies
    h.state('refreshToken', token);

    return h.response({
      error: false,
      message: 'Login successful',
      loginResult: {
        userId: user.id,
        name: user.username,
        token,
      },
    }).code(200);
  } catch (error) {
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const refreshTokenHandler = async (request, h) => {
  try {
    // Mendapatkan refreshToken dari cookies
    const { refreshToken } = request.state;

    if (refreshToken) {
      // Ambil informasi user dari refreshToken
      const decodedToken = jwt.verify(refreshToken, 'sehatin-aja');
      const { userId } = decodedToken;

      // Generate token baru
      const newToken = jwt.sign({ userId }, 'sehatin-aja', { expiresIn: '30m' });

      // Perbarui refreshToken dalam cookies
      h.state('refreshToken', newToken);

      return h.response({
        error: false,
        message: 'Refresh token successful',
        userId,
        newToken,
      }).code(200);
    }
    return h.response({
      error: true,
      message: 'Refresh token is invalid',
    }).code(401);
  } catch (error) {
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const logoutHandler = async (request, h) => {
  try {
    // Mendapatkan refreshToken dari cookies
    const { refreshToken } = request.state;

    if (refreshToken) {
    // Hapus refreshToken dari cookies
      h.unstate('refreshToken');

      return h.response({
        error: false,
        message: 'Logout successful',
      }).code(200);
    }
    return h.response({
      error: true,
      message: 'Logout is invalid',
    }).code(401);
  } catch (error) {
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
