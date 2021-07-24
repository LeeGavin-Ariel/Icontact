import jwt from 'jsonwebtoken';

export const getTokenInfo = () => {
  const accessToken = sessionStorage.getItem('access-token');
  if (accessToken) return jwt.decode(accessToken);
  return null;
};

export const tokenExpired = () => {
  const decodedToken = getTokenInfo();
  if (decodedToken) return decodedToken.exp < Date.now() / 1000 + 60;
  return false;
};
