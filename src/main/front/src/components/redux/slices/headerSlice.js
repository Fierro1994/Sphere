import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/api";

const initialState = {
    headerList: [],
    isPendDel: false,
    isFulDel: false,
    isRejDel: false
};

const PATH = 'http://localhost:3000/avatar/'


export const updateHeader = createAsyncThunk(
  "header/update",
  async (data,{ rejectWithValue }) => {
    
    try {
      const response = await instanceWidthCred.post(PATH +"listavatars", {
        userId: data,
      });
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const uploadHeader = createAsyncThunk(
  "header/upload",
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

export const deleteHeaderFile = createAsyncThunk(
    "header/deleteHeaderFile",
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


const headerSlice = createSlice({
  name: "header",
  initialState,
  reducers: {
    
    },
 
    
  extraReducers: (builder) => {
   
    builder.addCase(updateHeader.pending, (state, action) => {
      return { ...state, 
     };
    });
    builder.addCase(updateHeader.fulfilled, (state, action) => {
        return {
         ...state,
         headerList: action.payload,
         isPendDel: false
        };
    });
    builder.addCase(updateHeader.rejected, (state, action) => {
      return {
        ...state,
      };
    });
    builder.addCase(uploadHeader.fulfilled, (state, action) => {
      return {
        ...state,
        headerList: action.payload,
      
      };
    });
    builder.addCase(deleteHeaderFile.pending, (state, action) => {
      return { ...state, 
        isPendDel: true
     };
    });
    builder.addCase(deleteHeaderFile.fulfilled, (state, action) => {
      return {
        ...state,
        headerList: action.payload,
        isFulDel: true,
        
       };
    });
    builder.addCase(deleteHeaderFile.rejected, (state, action) => {
      return {
        ...state,
        isFulDel: false,
        isRejDel:true,
       
      };
    });
  },
  
});



export const {} = headerSlice.actions;

export default headerSlice.reducer;