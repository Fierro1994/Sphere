import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {jwtDecode} from "jwt-decode";
import { instanceWidthCred } from "../../auth/api/api";
import authService from "../../auth/services/authService";



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
  email: user2.email,
  _id: user2.id,
  activated: user2.activateEmail,
  roles: user2.roles,
  avatar: user2.avatar,
  registerStatus: "",
  registerError: "",
  loginStatus: "",
  loginError: "",
  userLoaded: false,
  isEnabled: false,
  onlineTime: localStorage.getItem("lastTimeOnline"),
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
      const token = await instanceWidthCred.post(`/api/auth/register`, {
        email: data.get("email"),
        firstName: data.get("firstName"),
        lastName: data.get("lastName"),
        password: data.get("password"),
        avatar: data.get("preview"),
        roles:[data.get("roles")]
      });
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
      const token = await instanceWidthCred.post(`/api/auth/signin`, {
        email: email,
        password: password,
        rememberMe: checkbox
      });
      const listItems = []
      token.data.body.itemsMenus.forEach( (item) =>{
        listItems.push({id: item.id, name:item.name,isEnabled: item.isEnabled, nametwo:item.nametwo})     
     })
     const listModulesMainPage = []
     token.data.body.listModulesMainPage.forEach( (item) =>{
      listModulesMainPage.push({id: item.id, name:item.name,isEnabled: item.isEnabled, nametwo:item.nametwo, pathImage: item.pathImage})     
   })
      localStorage.setItem("menuModules", JSON.stringify(listItems))
      localStorage.setItem("mainPageModules", JSON.stringify(listModulesMainPage))
      localStorage.setItem("access", token.data.body.accessToken);
      localStorage.setItem("theme",token.data.body.theme)
      return token.data;
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

export const getLastTimeOnline = createAsyncThunk(
  "auth/getLastTimeOnline",
  async ({_id}, { rejectWithValue }) => {
    try {
      const response = await instanceWidthCred.post(`/api/profile/settings/getlasttimeonline`, {
        userId: initialState._id
      });
      var date = new Date(response.data.body.localDateTime)
      var dateFormat = date.getHours() + ":" +  date.getMinutes()
      localStorage.setItem("lastTimeOnline",  dateFormat);
      return response.data.body.localDateTime;
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
        const user = jwtDecode(token);
        return {
          ...state,
          token,
          avatar: user.avatar,
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          _id: user.id,
          isEnabled: user.activateEmail,
        };
       
      } else return { ...state, userLoaded: true };
    },
 
    logoutUser(state, action) {
      authService.logout(state._id)
      localStorage.removeItem("menuModules");
      localStorage.removeItem("access");
      localStorage.removeItem("lastTimeOnline")
      localStorage.removeItem("mainPageModules");
      localStorage.removeItem("theme");
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
  onlineTime: ""
      };
    },
  },
  extraReducers: (builder) => {
   
    builder.addCase(registerUser.pending, (state, action) => {
      return { ...state, registerStatus: "pending",
      userLoaded: true };
    });
    builder.addCase(registerUser.fulfilled, (state, action) => {
        return {
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
        const user = jwtDecode(action.payload.body.accessToken);
        return {
          ...state,
          token: action.payload.body.accessToken,
          theme:user.theme,
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          _id: user.id,
          activated: user.activateEmail,
          roles: user.roles,
          avatar: user.avatar,
          loginStatus: "success",
          userLoaded: false,
          isEnabled: user.activateEmail
        };
      } else return state;
    });
    builder.addCase(loginUser.rejected, (state, action) => {
      return {
        ...state,
        loginStatus: "rejected",
        loginError: action.payload,
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