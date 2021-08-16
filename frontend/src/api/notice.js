import  { requestGet, requestPost, requestPut,requestDelete } from '@/utils/request.js';
import SERVER from './drf.js';

export const noticeApi = {

  createNotice: (data, headers) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.notice, data, headers);
  },

  getNotice: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.notice + `?noticeType=${data.noticeType}` + `&userId=${data.userId}` + `&pageNum=${data.pageNum}`, headers);
  },

  getNoticeDetail: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.notice + `/${data.noticeType}` + `?id=${data.id}`, headers);
  },

  updateNotice: (data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.notice, data, headers);
  },

  deleteNotice: ( data,headers ) => {
    return requestDelete(SERVER.URL + SERVER.ROUTES.notice + `?noticeType=${data.noticeType}` + `&id=${data.id}`, headers);
  },


};

export default noticeApi;