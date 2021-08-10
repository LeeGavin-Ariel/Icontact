import  { requestGet } from '@/utils/request.js';
import SERVER from './drf.js';

export const userApi = {
  getUserProfileImg: (userId) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.chat  + `/${userId}`);
  },
}

export default userApi;