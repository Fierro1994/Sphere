import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/instance";

const initialState = {
    promoList: [],
    isPendDel: false,
    isFulDel: false,
    isRejDel: false,
    isUpdFul: false,
    imageUrls: [],
    status: 'idle',
    error: null,
    
};

const PATH = 'imagepromo/'


export const updatePromo = createAsyncThunk(
    'promo/update',
    async (data, { rejectWithValue }) => {
        try {
            const response = await instanceWidthCred.get('/imagepromo/listpromo');
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

export const uploadPromo = createAsyncThunk(
  "promo/upload",
  async (data,{ rejectWithValue }) => {
    try {
      const config = {headers: {'Content-Type': 'multipart/form-data'}}
      const response = await instanceWidthCred.post(PATH +"upload", {
        file: data.get("file"),
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
        console.log(instanceWidthCred.getUri);
        const response = await instanceWidthCred.delete("imagepromo/"+ data.get("key"));
        
        
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
           isPendDel: false,
           isUpdFul: true
          };
      });
      builder.addCase(updatePromo.rejected, (state, action) => {
        return {
          ...state,   
        };
      });
      builder.addCase(uploadPromo.pending, (state, action) => {
        return { ...state, 
       };
      });
      builder.addCase(uploadPromo.fulfilled, (state, action) => {
        return {
          ...state,
          isUpdFul: false     
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