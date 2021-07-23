import axios from 'axios';


const setJwtTokens = (response) => {
  if (response.headers['jwt-access-token'])
    sessionStorage.setItem('access-token', response.headers['jwt-access-token']);
  if (response.headers['jwt-refresh-token'])
    sessionStorage.setItem('refresh-token', response.headers['jwt-refresh-token']);
};


export const requestPost = async (url, data, headers) => {
  axios({
    url: url,
    method: 'post',
    data: data,
    headers: {headers}
  })
  .then((res) => {
    if (res.stauts === 200) {
      if (res.headers['jwt-access-token']) {
        setJwtTokens(res)
        return res.data
      }
    }
  })
  .catch((err)=>{
    console.log(err)
  })
}
