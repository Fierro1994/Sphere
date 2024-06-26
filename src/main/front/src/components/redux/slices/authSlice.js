import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {jwtDecode} from "jwt-decode";
import {instance, instanceWidthCred} from "../../auth/api/instance";



let user2 = "";

if(localStorage.getItem("access")){
  const token = localStorage.getItem("access")
 user2 = jwtDecode(token);
}

const now = new Date();

const initialState = {
  token: localStorage.getItem("access"),
  theme: localStorage.getItem("theme"),
  firstName: user2.firstName,
  lastName: user2.lastName,
  email: localStorage.getItem("email"),
  _id: localStorage.getItem("userId"),
  activated: "",
  roles: "",
  avatar: "",
  registerStatus: "",
  registerError: "",
  loginStatus: "",
  loginError: "",
  userLoaded: false,
  isEnabled: false,
};



export const setSelectedTheme = createAsyncThunk(
  "auth/setSelectedTheme",
  async (data,{ rejectWithValue }) => {
    try {
      const response = await instanceWidthCred.post("/api/settings/interface/selecttheme", {
        userId: data.get("userId"),
        name: data.get("name")
      });
      localStorage.setItem("theme",response.data.body.name)
   return response.data.body.name
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const registerUser = createAsyncThunk(
  "auth/registerUser",
  async (data,{ rejectWithValue }) => {
     try {
      const token = await instance.post(`/api/auth/register`, data);
      return token.data;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const loginUser = createAsyncThunk(
  "auth/loginUser",
  async ({email, password, checkbox}, { rejectWithValue }) => {
    try {
      const response = await instance.post(`/api/auth/signin`, {
        email: email,
        password: password,
        rememberMe: checkbox
      });
      const listItems = []
      response.data.itemsMenus.forEach( (item) =>{
        listItems.push({id: item.id, name:item.name,isEnabled: item.isEnabled, nametwo:item.nametwo})     
     })


     const listModulesMainPage = []
     response.data.listModulesMainPage.forEach( (item) =>{
      listModulesMainPage.push({id: item.id, name:item.name,isEnabled: item.isEnabled, nametwo:item.nametwo, pathImage: item.pathImage})     
   })
      localStorage.setItem("menuModules", JSON.stringify(listItems))
      localStorage.setItem("userId", response.data.userId)
      localStorage.setItem("email", response.data.email)
      localStorage.setItem("mainPageModules", JSON.stringify(listModulesMainPage))
      localStorage.setItem("access", response.data.accessToken);
      localStorage.setItem("theme",response.data.theme)
      localStorage.setItem("avatar",JSON.stringify(response.data.avatar))
   
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);


const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loadUser(state, action) {
      const token = state.token;
      if (token) {
        return {
          ...state, 
        };
       
      } else return { ...state, userLoaded: true };
    },
 
    logoutUser(state, action) {
      localStorage.removeItem("menuModules");
      localStorage.removeItem("access");
      localStorage.removeItem("lastTimeOnline")
      localStorage.removeItem("mainPageModules");
      localStorage.removeItem("theme");
      localStorage.removeItem("userId");
      localStorage.removeItem("email");
      localStorage.removeItem("avatar");
      return {
        ...state,
        token: "",
        firstName: "",
          lastName: "",
          email: "",
          _id: "",
          activated: "",
          roles: "",
          avatar: "",
        registerStatus: "",
        registerError: "",
        loginStatus: "",
        loginError: "",
      };
    },
  },
  extraReducers: (builder) => {
   
    builder.addCase(registerUser.pending, (state, action) => {
      return { ...state, registerStatus: "pending",
      userLoaded: true };
    });
    builder.addCase(registerUser.fulfilled, (state, action) => {
        return {...state,
          registerStatus: "success",
          userLoaded: false
        };
    });
    builder.addCase(registerUser.rejected, (state, action) => {
      return {
        ...state,
        registerStatus: "rejected",
        registerError: action.payload,
      };
    });
    builder.addCase(loginUser.pending, (state, action) => {
      return { ...state, loginStatus: "pending" ,userLoaded: true};
    });
    builder.addCase(loginUser.fulfilled, (state, action) => {
      if (action.payload) {
        const user = jwtDecode(action.payload.accessToken);
        return {
          ...state,
          token: action.payload.accessToken,
          theme:action.payload.theme,
          firstName: user.firstName,
          lastName: user.lastName,
          email: action.payload.email,
          avatar: action.payload.avatar,
          _id: action.payload.userId,
          activated: user.activateEmail,
          roles: user.roles,
          loginStatus:"success",
          userLoaded: false,
          isEnabled: user.activateEmail
        };
      } else return state;
    });
    builder.addCase(loginUser.rejected, (state, action) => {
      return {
        ...state,
        loginStatus: "rejected",
        loginError: action.payload && action.payload.message,
      };
    });
  builder.addCase(setSelectedTheme.fulfilled, (state, action) => {
    return {...state,
      theme: action.payload
    };
  });
  
  },
  
});



export const { avatarUpload, loadUser, logoutUser } = authSlice.actions;

export default authSlice.reducer;