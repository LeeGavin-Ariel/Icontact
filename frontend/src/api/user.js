import  { requestPost, requestGet, requestPut } from '@/utils/request.js';
import SERVER from './drf.js';

export const userApi = {
  registerUser: (data) => {
    console.log('good')
    return requestPost(SERVER.URL + SERVER.ROUTES.signup, data);
  },


  getUser: (userId, headers) => {
    // 이거 유알엘 어떻게 하냐.
    // 근데 app.vue에서는 그냥 getUser 하나로만 갱신하는데....
    // url 바꿔주기.
    return requestGet(SERVER.URL + SERVER.ROUTES.getuserinf  + `/${userId}`, headers);
  },




  updateUser: (data, headers) => {
    return requestPut(`${process.env.VUE_APP_API_ENDPOINT}/user`, data, headers);
  },

  getTeacher: (userId, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.getteacherinf  + `/${userId}`, headers);
  },
  getParent: (profileId, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.getparentinf  + `/${profileId}`, headers);
  },

  updateTeacher: (userId, data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.updateteacherinf + `/${userId}`, data, headers);
  },

  updateParrent: (profileId, data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.updateParrent + `/${profileId}`, data, headers);
  },

}

export default userApi;