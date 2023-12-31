const { nanoid } = require('nanoid');
const jwt = require('jsonwebtoken');
const { promisify } = require('util');
const db = require('../config/configdb');

const query = promisify(db.query).bind(db);

const predictHandler = async (request, h) => {
  try {
    const {
      age, height, weight,
    } = request.payload;

    const data = "Cows milk";

    return h.response({
      error: false,
      message: 'Success',
      umur: age,
      tinggi: height,
      berat: weight,
      predict: data,
    }).code(201);
  } catch (error) {
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const addHistoryHandler = async (request, h) => {
  try {
    const {
      age, height, weight, data,
    } = request.payload;

    // Mendapatkan refreshToken dari cookies
    const { refreshToken } = request.state;

    if (refreshToken) {
      // Ambil informasi user dari refreshToken
      const decodedToken = jwt.verify(refreshToken, 'sehatin-aja');
      const { userId } = decodedToken;
      const user = userId;

      // Tambahkan user ke database
      const id = nanoid(10);
      const insertDate = new Date();
      const formatDate = insertDate.toLocaleDateString('en-US', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      }).replace(/\//g, '-');

      const insertHistory = `INSERT INTO histories (id, userId, age, height, weight, data, tanggal) VALUES ('${id}', '${user}', '${age}', '${height}', '${weight}', '${data}', '${formatDate}')`;
      await query(insertHistory);

      return h.response({
        error: false,
        message: 'Success',
        user,
      }).code(201);
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

const getAllHistoryHandler = async (request, h) => {
  try {
    // Mendapatkan refreshToken dari cookies
    const { refreshToken } = request.state;

    if (refreshToken) {
      // Ambil informasi user dari refreshToken
      const decodedToken = jwt.verify(refreshToken, 'sehatin-aja');
      const { userId } = decodedToken;

      // Ambil data user dari database
      const getHistory = `SELECT * FROM histories WHERE userId='${userId}'`;
      const histories = await query(getHistory);

      if (!histories) {
        return h.response({
          error: true,
          message: 'histories not found',
        }).code(404);
      }

      return h.response({
        error: false,
        message: 'Success',
        histories,
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

const getHistoryByIdHandler = async (request, h) => {
  try {
    const { id } = request.params;

    // Ambil data user dari database
    const getHistory = `SELECT * FROM histories WHERE id='${id}'`;
    const rows = await query(getHistory);
    const history = rows[0];

    if (!history) {
      return h.response({
        error: true,
        message: 'History not found',
      }).code(404);
    }

    return h.response({
      error: false,
      message: 'Success',
      history,
    });
  } catch (error) {
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

const deleteHistoryByIdHandler = async (request, h) => {
  try {
    const { id } = request.params;

    // Ambil data user dari database
    const getHistory = `SELECT * FROM histories WHERE id='${id}'`;
    const rows = await query(getHistory);
    const history = rows[0];

    if (!history) {
      return h.response({
        error: true,
        message: 'History not found',
      }).code(404);
    }

    const delHistory = `DELETE FROM histories WHERE id='${id}'`;
    await query(delHistory);

    return h.response({
      error: false,
      message: 'Delete Success',
    });
  } catch (error) {
    return h.response({
      error: true,
      message: 'Terjadi kesalahan pada server',
    }).code(500);
  }
};

module.exports = {
  predictHandler,
  addHistoryHandler,
  getAllHistoryHandler,
  getHistoryByIdHandler,
  deleteHistoryByIdHandler,
};

