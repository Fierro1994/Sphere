
import { useDispatch } from "react-redux";
import { logoutUser } from "../../redux/slices/authSlice";
import { instance, instanceWidthCred } from "../api/RequireAuth";




export const refresh = async () => {
  try {
    console.log("sdfsdf");
    const response = await instanceWidthCred.post("/api/auth/refresh",{
    });
    if (response.status.valueOf(404))
     {
      localStorage.removeItem("access");
      localStorage.removeItem("menuModules");
    
    }
    if(response.data.body.accessToken){localStorage.setItem("access",response.data.body.accessToken)}

    console.log(response);
     
    return response.data;
  } catch (error) {
    console.log(error);
  }
  
};


const Logout = () => {
  localStorage.removeItem("menuModules");
  localStorage.removeItem("access");
  localStorage.removeItem("lastTimeOnline")
  localStorage.removeItem("mainPageModules");
  localStorage.removeItem("theme");
  localStorage.removeItem("userId");
  localStorage.removeItem("email");
  localStorage.removeItem("avatar");
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

const authService = { Logout, SetOnlineTime};

export default authService;
