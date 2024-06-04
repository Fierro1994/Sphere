import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/RequireAuth";


const initialState = {
  listMainPageModule:[],
};


export const updateMainPageModule = createAsyncThunk(
  "mainpage/updateMainPageModule",
  async (data,{ rejectWithValue }) => {
   
    try {
      await instanceWidthCred.post("/api/mainpage/settings/updatemodule", {
        userId: data.get("userId"),
        name: data.getAll("name")
      });
     localStorage.removeItem("mainPageModules")
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const getMainPageModules = createAsyncThunk(
  "mainpage/getMainPageModules",
  async (data,{ rejectWithValue }) => {
    const listmodules = []
    if(!localStorage.getItem("mainPageModules")){
    try {
      const response = await instanceWidthCred.post("/api/mainpage/settings/getmodule", {
        userId: data
      });
      (response.data.body.menu).forEach( (item) =>{
        
        listmodules.push({id: item.id, name:item.name,isEnabled: item.isEnabled, nametwo:item.nametwo, pathImage: item.pathImage})     
       })
       localStorage.setItem("mainPageModules", JSON.stringify(listmodules))
      return listmodules
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
  }
);



const mainPageModuleSlice = createSlice({
    name: "mainpage",
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
     builder.addCase(getMainPageModules.fulfilled, (state, action) => {
        return {...state,
            listMainPageModule: JSON.parse(localStorage.getItem("mainPageModules"))
        };
    });
    },
  });
  



export const { } = mainPageModuleSlice.actions;

export default mainPageModuleSlice.reducer;