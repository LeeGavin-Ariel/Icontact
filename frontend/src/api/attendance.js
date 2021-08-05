import  {requestGet, requestPost} from '@/utils/request.js';
import SERVER from './drf.js';

export const attendanceApi = {

  getChildren: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.attendance + '/teacher' + `?userId=${data.userId}` + `&date=${data.date}`, headers)
  },

  getChild: (data, headers) => {
    return requestGet(SERVER.URL + SERVER.ROUTES.attendance + '/parents' + `?${data.userId}` + `&${data.date}`, headers)
  },

  updateAttendence: (data, headers) => {
    return requestPost(SERVER.URL + SERVER.ROUTES.request, data, headers);
  },

};

export default attendanceApi;