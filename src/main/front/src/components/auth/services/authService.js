
import { instance, instanceWidthCred } from "../api/api";




const refresh = async () => {
  try {
    const response = await instanceWidthCred.post("/api/auth/refresh",{
    });
    if (response.status.valueOf(404))
     {
      localStorage.removeItem("access");
      localStorage.removeItem("menuModules");
    
    }
    if(response.data.body.accessToken){localStorage.setItem("access",response.data.body.accessToken)}
     
    return response.data;
  } catch (error) {
    console.log(error);
  }
  
};


const logout = async (_id) => {
  try {
    const response = await instance.post(`/api/auth/logout`, {
      userId: _id
    });
   return response;
  } catch (error) {
    console.log(error);
  }
 
} 




const SetOnlineTime = async (_id) => {
  
  try {
   

    const response = await instanceWidthCred.post("/api/profile/settings/setlasttimeonline", {
      userId: _id
    });
   return response;
  } catch (error) {
    console.log(error);
  }
 
} 

const authService = { refresh , logout, SetOnlineTime};

export default authService;
