import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/instance";

const initialState = {
    momentsList: [],
    isPendDel: false,
    isFulDel: false,
    isRejDel: false,
    isUplFul: false,
    isUplPen: false,
    isEndMoments: false,
    isUpdFull: false

};

const PATH = 'moments/'


export const updateMoments = createAsyncThunk(
  "moments/update",
  async (data,{ rejectWithValue }) => {
    
    try {
      const response = await instanceWidthCred.post(PATH +"listmoments", {
        userId: data,
      });
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const uploadMoments = createAsyncThunk(
  "moments/upload",
  async (data,{ rejectWithValue }) => {
    try {
      const config = {headers: {'Content-Type': 'multipart/form-data'}}
      const response = await instanceWidthCred.post(PATH +"upload", {
        userId: data.get("id"),
        file: data.get("file"),
        startTrim: data.get("startTrim"),
        endTrim: data.get("endTrim")
      }, config);
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);

export const uploadMomImage = createAsyncThunk(
  "header/upload_img",
  async (data,{ rejectWithValue }) => {
    try {
      const config = {headers: {'Content-Type': 'multipart/form-data'}}
      const response = await instanceWidthCred.post(PATH +"upload_img", {
        userId: data.get("id"),
        file: data.get("file"),
        size: data.get("size"),
        name: data.get("name"),
        type: data.get("type")
      }, config);
                return response.data.body;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);



export const deleteMoments = createAsyncThunk(
    "moments/deletePromoFile",
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


const momentsSlice = createSlice({
  name: "moments",
  initialState,
  reducers: {
    startMoment(state, action) {
      return {
        ...state,
        isEndMoments: false
      };
    },
    endMoment(state, action) {
      return {
        ...state,
        isEndMoments: true
      };
    }
    },
 extraReducers: (builder) => {
  
  builder.addCase(uploadMomImage.pending, (state, action) => {
    return { ...state,
      isUplFul: false,
      isUplPen: true
   };
  });
  builder.addCase(uploadMomImage.fulfilled, (state, action) => {
    return {
      ...state,
      momentsList: action.payload,
      isUplFul: true,
      isUplPen: false
    
    };
  });
  builder.addCase(uploadMomImage.rejected, (state, action) => {
    return {
      ...state,
      isUplFul: false
      , isUplPen: false
    };
  });
      builder.addCase(updateMoments.pending, (state, action) => {
        return { ...state, 
       };
      });
      builder.addCase(updateMoments.fulfilled, (state, action) => {
          return {
           ...state,
           momentsList: action.payload,
           isPendDel: false,
           isUplFul: true,
           isUpdFull: true
          };
      });
      builder.addCase(updateMoments.rejected, (state, action) => {
        return {
          ...state,
          isUplFul: false
        };
      });
      builder.addCase(uploadMoments.pending, (state, action) => {
        return { ...state,
          isUplFul: false,
          isUplPen: true
       };
      });
      builder.addCase(uploadMoments.fulfilled, (state, action) => {
        return {
          ...state,
          momentsList: action.payload,
          isUplFul: true,
          isUplPen: false
        
        };
      });
      builder.addCase(uploadMoments.rejected, (state, action) => {
        return {
          ...state,
          isUplFul: false
          , isUplPen: false
        };
      });
      builder.addCase(deleteMoments.pending, (state, action) => {
        return { ...state, 
          isPendDel: true
       };
      });
      builder.addCase(deleteMoments.fulfilled, (state, action) => {
        return {
          ...state,
          momentsList: action.payload,
          isFulDel: true,
          isUplFul: true
          
         };
      });
      builder.addCase(deleteMoments.rejected, (state, action) => {
        return {
          ...state,
          isFulDel: false,
          isRejDel:true,
          isUplFul: false
         
        };
      });
    },
    
  });



export const {startMoment, endMoment} = momentsSlice.actions;

export default momentsSlice.reducer;