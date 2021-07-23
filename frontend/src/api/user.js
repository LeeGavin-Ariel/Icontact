import  { requestPost } from '@/utils/request.js';
import SERVER from './drf.js';

const userApi = {
  registerUser: (data) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.signup, data);
  },
}

export default userApi;