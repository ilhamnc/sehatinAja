const handler = require('./handler');

const routes = [
  {
    method: 'POST',
    path: '/register',
    handler: handler.addUsersHandler,
  },
  {
    method: 'GET',
    path: '/profile',
    handler: handler.getUserHandler,
  },
  {
    method: 'POST',
    path: '/login',
    handler: handler.loginHandler,
  },
  {
    method: 'PUT',
    path: '/refreshToken',
    handler: handler.refreshTokenHandler,
  },
  {
    method: 'DELETE',
    path: '/logout',
    handler: handler.logoutHandler,
  },
];

module.exports = routes;
