import  { requestGet, requestPost, requestPut, requestDelete } from '@/utils/request.js';
import SERVER from './drf.js';

export const menuApi = {

  createMenu: (data, headers) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.menu, data, headers);
  },

  getMenu: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.menu + `?noticeType=${data.menuType}` + `&userId=${data.userId}` + `&pageNum=${data.pageNum}`, data, headers);
  },


  getMenuDetail: (data, headers) => {    
    return requestGet(SERVER.URL + SERVER.ROUTES.menu + `/${data.menuType}` + `?id=${data.id}`, headers);
  },
  
  updateMenu: (data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.menu, data, headers);
  },

  deleteMenu: ( data,headers ) => {
    return requestDelete(SERVER.URL + SERVER.ROUTES.menu + `?noticeType=${data.menuType}` + `&id=${data.id}`, headers);
  },


};

export default menuApi;