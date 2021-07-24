import  { requestPost, requestGet, requestPut } from '@/utils/request.js';
import SERVER from './drf.js';

const userApi = {
  registerUser: (data) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.signup, data);
  },
  getUser: (userId, headers) => {
    return requestGet(`${process.env.VUE_APP_API_ENDPOINT}/user/${userId}`, headers);
  },
  updateUser: (data, headers) => {
    return requestPut(`${process.env.VUE_APP_API_ENDPOINT}/user`, data, headers);
  },
}

export default userApi;