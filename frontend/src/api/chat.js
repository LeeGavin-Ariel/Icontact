import  { requestGet } from '@/utils/request.js';
import SERVER from './drf.js';

export const chatApi = {
  getUserProfileImg: (userId, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.chat  + `/${userId}`, headers);
  },

  getUserStatus: (userId, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.chat  + `/stateCode/${userId}`, headers);
  },
}

export default chatApi;