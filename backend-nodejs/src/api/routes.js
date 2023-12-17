const auth = require('./auth');
const history = require('./history');

const routes = [
  {
    method: 'POST',
    path: '/register',
    handler: auth.addUsersHandler,
  },
  {
    method: 'GET',
    path: '/profile',
    handler: auth.getUserHandler,
  },
  {
    method: 'POST',
    path: '/login',
    handler: auth.loginHandler,
  },
  {
    method: 'PUT',
    path: '/refreshToken',
    handler: auth.refreshTokenHandler,
  },
  {
    method: 'DELETE',
    path: '/logout',
    handler: auth.logoutHandler,
  },
  {
    method: 'POST',
    path: '/generate',
    handler: history.addHistoryHandler,
  },
  {
    method: 'GET',
    path: '/history',
    handler: history.getAllHistoryHandler,
  },
  {
    method: 'GET',
    path: '/history/{id}',
    handler: history.getHistoryByIdHandler,
  },
  {
    method: 'DELETE',
    path: '/history/{id}',
    handler: history.deleteHistoryByIdHandler,
  },
];

module.exports = routes;
