import axios from 'axios';


const setJwtTokens = (response) => {
  if (response.headers['jwt-access-token'])
    sessionStorage.setItem('access-token', response.headers['jwt-access-token']);
  if (response.headers['jwt-refresh-token'])
    sessionStorage.setItem('refresh-token', response.headers['jwt-refresh-token']);
};

const updateAccessToken = (response) => {
  if (response.headers['jwt-access-token'] !== sessionStorage.getItem('access-token')) {
    sessionStorage.setItem('access-token', response.headers['jwt-access-token']);
  }
};

export const requestGet = async (url, headers) => {
  try {
    const response = await axios.get(url, { headers });
    if (response.status === 200) {
      if (response.headers['jwt-access-token']) updateAccessToken(response);
      return response.data;
    }
    throw new Error();
  } catch (e) {

    throw new Error(e);
  }
};

export const requestPost = async (url, data, headers) => {
  try {
    const response = await axios.post(url, data, { headers });
    if (response.status === 200) {
      if (response.headers['jwt-access-token']) setJwtTokens(response);
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};

export const requestSendBirdPost = async (url, data, headers) => {
  console.log(url);
  console.log(headers);
  console.log(data)
  try {
    const response = await axios.post(url, data, { headers });
    if (response.status === 200) {
      console.log('성공했습니다.');
      console.log(response.data);
      // if (response.headers['jwt-access-token']) setJwtTokens(response);
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};

export const requestPut = async (url, data, headers) => {
  try {
    const response = await axios.put(url, data, { headers });
    if (response.status === 200) {
      if (response.headers['jwt-access-token']) updateAccessToken(response);
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};


export const requestDelete = async (url, headers) => {
  try {
    const response = await axios.delete(url, { headers });
    if (response.status === 200) {
      if (response.headers['jwt-access-token']) updateAccessToken(response);
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};
