import axios from "axios";


export const instance = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
      "Content-Type": "application/json",
    },
  });
  
  export const instanceWidthCred = axios.create({
      baseURL: "http://localhost:8080",
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
  
  })
  
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("access")
      config.headers.Authorization = `Bearer ${token}`;
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
  
  instance.interceptors.response.use(
    (res) => {
      return res;
    },
    async (err) => {
      const originalConfig = err.config;
  
      if (originalConfig.url !== "/**" && err.response) {
      
        if (err.response.status === 401 && !originalConfig._retry) {
          originalConfig._retry = true;
  
          try {
            const rs = await instance.post("api/auth/refresh", {withCredentials:true,
            });
  
            const { accessToken } = rs.data.body.accessToken;
            localStorage.setItem("access", accessToken)
            return instance(originalConfig);
          } catch (_error) {
            return Promise.reject(_error);
          }
        }
      }
  
      return Promise.reject(err);
    }
  );
  