import  { requestGet, requestPost, requestDelete } from '@/utils/request.js';
import SERVER from './drf.js';

export const menuApi = {

  createMenu: (data, headers) => {
    console.log(SERVER.URL + SERVER.ROUTES.menu);
    console.log(data);
    console.log(headers);
    return requestPost(SERVER.URL + SERVER.ROUTES.menu, data, headers);
  },

  getMenu: (data, headers) => {
    console.log('data');
    console.log(data);
    return requestGet(SERVER.URL + SERVER.ROUTES.menu + `?noticeType=${data.menuType}` + `&userId=${data.userId}` + `&pageNum=${data.pageNum}`, data, headers);
  },


  getMenuDetail: (data, headers) => {    
      console.log('디테일');
      console.log(data.menuType);
      console.log(SERVER.URL + SERVER.ROUTES.menu);
    return requestGet(SERVER.URL + SERVER.ROUTES.menu + `/${data.menuType}` + `?id=${data.id}`, headers);
  },
  
  updateMenu: (data, headers) => {
    console.log(SERVER.URL + SERVER.ROUTES.menu);
    return requestPost(SERVER.URL + SERVER.ROUTES.menu + `/2`, data, headers);
    // return requestPost(SERVER.URL + SERVER.ROUTES.menu + `/${data.menuType}`, data, headers);
  },

  deleteMenu: ( data,headers ) => {
    return requestDelete(SERVER.URL + SERVER.ROUTES.menu + `?noticeType=${data.menuType}` + `&id=${data.id}`, headers);
  },


};

export default menuApi;