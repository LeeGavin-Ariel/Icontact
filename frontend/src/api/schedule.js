import  { requestGet, requestPost, requestPut,requestDelete } from '@/utils/request.js';
import SERVER from './drf.js';

export const scheduleApi = {

  createSchedule: (data, headers) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.schedule, data, headers);
  },

  getSchedule: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.schedule + `?noticeType=${data.scheduleType}` + `&userId=${data.userId}` + `&pageNum=${data.pageNum}`, data, headers);
  },


  getScheduleDetail: (data, headers) => {    
    return requestGet(SERVER.URL + SERVER.ROUTES.schedule + `/${data.scheduleType}` + `?id=${data.id}`, headers);
  },
  
  updateSchedule: (data, headers) => { 
    return requestPut(SERVER.URL + SERVER.ROUTES.schedule, data, headers);
  },

  deleteSchedule: ( data,headers ) => {
    return requestDelete(SERVER.URL + SERVER.ROUTES.schedule + `?noticeType=${data.scheduleType}` + `&id=${data.id}`, headers);
  },


};

export default scheduleApi;