import { requestPost } from '@/utils/request.js';
import SERVER from './drf.js';

const authApi = {
  login: (data) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.login, data);
  },
};

export default authApi;