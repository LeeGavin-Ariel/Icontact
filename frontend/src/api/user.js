import  { requestPost, requestGet, requestPut } from '@/utils/request.js';
import SERVER from './drf.js';

export const userApi = {
  registerUser: (data) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.signup, data);
  },

  updateUser: (data) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.logout + `?userId=${data.userId}`);
  },

  getUser: (userId, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.getuserinf  + `/${userId}`, headers);
  },

  updateUserPassword: (data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.changePWAfterLogin + `/${data.userId}`, data, headers);
  },
  

  updateUserProfileImg: (data, headers) => {    
    return requestPut(SERVER.URL + SERVER.ROUTES.changeProfileImg + `/${data.userId}?profileImg=${data.profileImg}`, data, headers);
  },
  
  updateUserStateCode: (data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.changeStateCode + `/${data.userId}?stateCode=${data.stateCode}`, data, headers);
  },

}

export default userApi;