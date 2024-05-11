import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/api";

const initialState = {
    infoList: [],
    isPendDel: false,
    isFulDel: false,
    isRejDel: false

};

const PATH = 'http://localhost:3000/infomodule/'


export const updateInfo = createAsyncThunk(
  "info/update",
  async (data,{ rejectWithValue }) => {
    
    try {
      const response = await instanceWidthCred.post(PATH +"listmodules", {
        userId: data,
      });
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const uploadInfo = createAsyncThunk(
  "info/upload",
  async (data,{ rejectWithValue }) => {
    try {
      const config = {headers: {'Content-Type': 'multipart/form-data'}}
      const response = await instanceWidthCred.post(PATH +"upload", {
        userId: data.get("id"),
        file: data.get("file"),
        size: data.get("size"),
        name: data.get("name"),
      }, config);
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const deleteInfo = createAsyncThunk(
    "info/deleteInfo",
    async (data,{ rejectWithValue }) => {
      try {
        const response = await instanceWidthCred.delete(PATH + data.get("id") + "/" + data.get("key"));
        
        return response.data.body;
      } catch (error) {
        console.log(error.response.data);
        return rejectWithValue(error.response.data);
      }
    }
  );


const infoMpSlice = createSlice({
  name: "info",
  initialState,
  reducers: {
    },
 
    
    extraReducers: (builder) => {
   
      builder.addCase(updateInfo.pending, (state, action) => {
        return { ...state, 
       };
      });
      builder.addCase(updateInfo.fulfilled, (state, action) => {
          return {
           ...state,
           infoList: action.payload,
           isPendDel: false
          };
      });
      builder.addCase(updateInfo.rejected, (state, action) => {
        return {
          ...state,
        };
      });
      builder.addCase(uploadInfo.fulfilled, (state, action) => {
        return {
          ...state,
          infoList: action.payload,
        
        };
      });
      builder.addCase(deleteInfo.pending, (state, action) => {
        return { ...state, 
          isPendDel: true
       };
      });
      builder.addCase(deleteInfo.fulfilled, (state, action) => {
        return {
          ...state,
          infoList: action.payload,
          isFulDel: true,
          
         };
      });
      builder.addCase(deleteInfo.rejected, (state, action) => {
        return {
          ...state,
          isFulDel: false,
          isRejDel:true,
         
        };
      });
    },
    
  });



export const {} = infoMpSlice.actions;

export default infoMpSlice.reducer;