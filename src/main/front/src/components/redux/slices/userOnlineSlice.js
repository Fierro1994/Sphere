import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/instance";



const now = new Date();

const initialState = {
  onlineTime: localStorage.getItem("lastTimeOnline"),
};


export const setLastTimeOnline = createAsyncThunk(
    "online/setLastTimeOnline",
    async (userId, { rejectWithValue }) => {
      try {
        const response = await instanceWidthCred.post(`/api/profile/settings/setlasttimeonline`, {
          userId: userId
        });
      var dateFormat = response.data.body.localDateTime[3] + ":" + response.data.body.localDateTime[4]
      localStorage.setItem("lastTimeOnline",  dateFormat);
        return response.data.body.localDateTime;
      } catch (error) {
        return rejectWithValue(error.response.data);
      }
    }
  );
  


const userOnlineSlice = createSlice({
  name: "online",
  initialState,
  reducers: {
  },
  extraReducers: (builder) => {
   
    builder.addCase(setLastTimeOnline.pending, (state, action) => {
      return { ...state,};
    });
    builder.addCase(setLastTimeOnline.fulfilled, (state, action) => {
        return {
            ...state,
            onlineTime: action.payload
        };
    });
    builder.addCase(setLastTimeOnline.rejected, (state, action) => {
      return {
        ...state,
      };
    });
  },
  
});



export const { } = userOnlineSlice.actions;

export default userOnlineSlice.reducer;