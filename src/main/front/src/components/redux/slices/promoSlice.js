import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/api";

const initialState = {
    promoList: [],
    isPendDel: false,
    isFulDel: false,
    isRejDel: false

};

const PATH = 'http://localhost:3000/imagepromo/'


export const updatePromo = createAsyncThunk(
  "promo/update",
  async (data,{ rejectWithValue }) => {
    
    try {
      const response = await instanceWidthCred.post(PATH +"listpromo", {
        userId: data,
      });
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const uploadPromo = createAsyncThunk(
  "promo/upload",
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

export const deletePromoFile = createAsyncThunk(
    "promo/deletePromoFile",
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


const promoSlice = createSlice({
  name: "promo",
  initialState,
  reducers: {
    loadPromoImg(state, action) {
        return {
          ...state,
          promoList: []
        };
      }
    },
 
    
    extraReducers: (builder) => {
   
      builder.addCase(updatePromo.pending, (state, action) => {
        return { ...state, 
       };
      });
      builder.addCase(updatePromo.fulfilled, (state, action) => {
          return {
           ...state,
           promoList: action.payload,
           isPendDel: false
          };
      });
      builder.addCase(updatePromo.rejected, (state, action) => {
        return {
          ...state,
        };
      });
      builder.addCase(uploadPromo.fulfilled, (state, action) => {
        return {
          ...state,
          promoList: action.payload,
        
        };
      });
      builder.addCase(deletePromoFile.pending, (state, action) => {
        return { ...state, 
          isPendDel: true
       };
      });
      builder.addCase(deletePromoFile.fulfilled, (state, action) => {
        return {
          ...state,
          promoList: action.payload,
          isFulDel: true,
          
         };
      });
      builder.addCase(deletePromoFile.rejected, (state, action) => {
        return {
          ...state,
          isFulDel: false,
          isRejDel:true,
         
        };
      });
    },
    
  });



export const {loadGallery} = promoSlice.actions;

export default promoSlice.reducer;