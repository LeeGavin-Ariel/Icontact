import  { requestGet, requestPost, requestPut, requestDelete } from '@/utils/request.js';
import SERVER from './drf.js';

export const requestApi = {

  createRequest: (data, headers) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.request, data, headers);
  },


  getRequest: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.request + `/${data.type}` + `?requestType=${data.requestType}`+`&userId=${data.userId}`, headers);
  },


  getRequestDetail: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.request + `?requestType=${data.requestType}` + `&id=${data.id}`, headers);
  },

  updateRequest: (data, headers) => {
    return requestPut(SERVER.URL + SERVER.ROUTES.request, data, headers);
  },

  deleteRequest: ( data,headers ) => {
    return requestDelete(SERVER.URL + SERVER.ROUTES.request + `?requestType=${data.requestType}` + `&id=${data.id}`, headers);
  },


};

export default requestApi;