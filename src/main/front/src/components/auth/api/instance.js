import axios from "axios";

export const instanceWidthCred = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true // Этот флаг позволяет отправлять куки вместе с запросом

  });


let isRefreshing = false;
let refreshSubscribers = [];

function subscribeTokenRefresh(cb) {
  refreshSubscribers.push(cb);
}

function onRefreshed(token) {
  refreshSubscribers.map(cb => cb(token));
  refreshSubscribers = [];
}

instanceWidthCred.interceptors.response.use(
  response => response,
  async error => {
    const { config, response: { status } } = error;
    const originalRequest = config;
    if (status === 401 && !originalRequest._retry) {
      if (!isRefreshing) {
  
        isRefreshing = true;
        originalRequest._retry = true;
        try {
          const response = await instanceWidthCred.get('api/auth/refresh', {
          });

          const { accessToken } = response.data;
          localStorage.setItem('access', accessToken);
          isRefreshing = false;
          onRefreshed(accessToken);

          return instanceWidthCred(originalRequest);
        } catch (err) {
          isRefreshing = false;
          localStorage.clear();
        }
      }

      return new Promise(resolve => {
          subscribeTokenRefresh(token => {
              originalRequest.headers['Authorization'] = 'Bearer ' + token;
              resolve(instanceWidthCred(originalRequest));
          });
      });
    }

    return Promise.reject(error);
  }
);

instanceWidthCred.interceptors.request.use(async (config) => {
  const token = localStorage.getItem('access');
  // if (token) {
    
  //   const decodedToken = jwt_decode(token)
  //   const currentTime = Date.now() / 1000;

  //   if (decodedToken.exp < currentTime + 5 * 60) {
  //     try {
  //       const response = await instanceWidthCred.get('api/auth/refresh');
  //       const newAccessToken = response.data.accessToken;
      
  //       localStorage.setItem('access', newAccessToken);
  //               config.headers['Authorization'] = `Bearer ${newAccessToken}`;
  //     } catch (error) {
  //       console.error('Ошибка обновления токена', error);
  //     }
  //   } else {
      config.headers['Authorization'] = `Bearer ${token}`;
  //   }
  // }
  return config;
}, (error) => {
  return Promise.reject(error);
});