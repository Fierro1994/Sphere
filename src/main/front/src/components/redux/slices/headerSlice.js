import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/instance";

const initialState = {
    headerList: [],
    isUpdFull: false,
    isPendDel: false,
    isFulDel: false,
    isRejDel: false
};

const PATH = 'avatar/'


export const updateHeader = createAsyncThunk(
  'header/update',
  async (data, { rejectWithValue }) => {
    try {
      const response = await instanceWidthCred.get('avatar/listavatars');
      const urls = response.data.map((promo) => promo);

      const fetchImage = async (url) => {
        const response = await instanceWidthCred.get(url, { responseType: 'blob' });
        return URL.createObjectURL(response.data);
      };

      const imageUrlsList = await Promise.all(urls.map((url)=> fetchImage(url)));
   
      return imageUrlsList
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
        avatar: data.get("avatar"),      
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
      localStorage.setItem("avatar", action.payload[0])
        return {
         ...state,
         headerList: action.payload,
         isPendDel: false,
         isUpdFull: true,
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
        isUpdFull: false
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