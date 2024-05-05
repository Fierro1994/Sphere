import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/api";

const initialState = {
    galleryList: []
};

const PATH = 'http://localhost:3000/gallery/'


export const updateGallery = createAsyncThunk(
  "gallery/updateGallery",
  async (data,{ rejectWithValue }) => {
   
    try {
      const response = await instanceWidthCred.post(PATH +"listgallery", {
        userId: data,
      });
      return response;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const deleteGaleryFile = createAsyncThunk(
    "gallery/deleteGaleryFile",
    async (data,{ rejectWithValue }) => {
     
      try {
        const response = await instanceWidthCred.delete(PATH + data.get("id") + "/" + data.get("key"));
        return response;
      } catch (error) {
        console.log(error.response.data);
        return rejectWithValue(error.response.data);
      }
    }
  );


const galerySlice = createSlice({
  name: "gallery",
  initialState,
  reducers: {
    loadGallery(state, action) {
        return {
          ...state,
          galleryList: []
        };
      }
    },
 
    
  extraReducers: (builder) => {
   
    builder.addCase(updateGallery.pending, (state, action) => {
      return { ...state, 
     };
    });
    builder.addCase(updateGallery.fulfilled, (state, action) => {
      
        return {
         ...state,
         galleryList: action.payload.data.body
        };
    });
    builder.addCase(updateGallery.rejected, (state, action) => {
      return {
        ...state,
      };
    });
    builder.addCase(deleteGaleryFile.fulfilled, (state, action) => {
        return {
         ...state
        };
    });
  },
  
});



export const {loadGallery} = galerySlice.actions;

export default galerySlice.reducer;