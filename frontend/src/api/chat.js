import  { requestGet } from '@/utils/request.js';
import SERVER from './drf.js';

export const userApi = {
  //프로필 이미지와 상태코드를 가져온다
  getUserProfileImg: (userId) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.chat  + `/${userId}`);
  },

  getUserStatus: (userId) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.chat  + `/stateCode/${userId}`);
  },
}

export default userApi;