import  { requestGet, requestPost, requestDelete } from '@/utils/request.js';
import SERVER from './drf.js';

export const scheduleApi = {

  createSchedule: (data, headers) => {
    console.log(SERVER.URL + SERVER.ROUTES.schedule);
    console.log(data);
    console.log(headers);
    return requestPost(SERVER.URL + SERVER.ROUTES.schedule, data, headers);
  },

  getSchedule: (data, headers) => {
    console.log('data');
    console.log(data);
    return requestGet(SERVER.URL + SERVER.ROUTES.schedule + `?noticeType=${data.scheduleType}` + `&userId=${data.userId}` + `&pageNum=${data.pageNum}`, data, headers);
  },


  getScheduleDetail: (data, headers) => {    
      console.log('디테일');
      console.log(data.scheduleType);
      console.log(SERVER.URL + SERVER.ROUTES.schedule);
    return requestGet(SERVER.URL + SERVER.ROUTES.schedule + `/${data.scheduleType}` + `?id=${data.id}`, headers);
  },
  
  updateSchedule: (data, headers) => { 
    console.log('updateSchedule');
    console.log(data.get('scheduleType'));
    console.log(SERVER.URL + SERVER.ROUTES.schedule);
    return requestPost(SERVER.URL + SERVER.ROUTES.schedule + `/2`, data, headers);
    // return requestPost(SERVER.URL + SERVER.ROUTES.schedule + `/${data.scheduleType}`, data, headers);
  },

  deleteSchedule: ( data,headers ) => {
    return requestDelete(SERVER.URL + SERVER.ROUTES.schedule + `?noticeType=${data.scheduleType}` + `&id=${data.id}`, headers);
  },


};

export default scheduleApi;