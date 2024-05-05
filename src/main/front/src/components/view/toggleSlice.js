import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";


const initialState = {
  toggle: true,
};

export const getToggle = createAsyncThunk(
  "toggle/getToggle",
  async (data, { rejectWithValue }) => {

    try {
      return data;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);


const toggleSlice = createSlice({
  name: "toggle",
  initialState,
  reducers: {
    setSlice(state, action) {
      return { ...state, toggle: action.payload }
    }
  },
  extraReducers: (builder) => {
    builder.addCase(getToggle.fulfilled, (state, action) => {
      return {
        ...state,
        toggle: action.payload
      };
    });
  },
});



export const { setSlice } = toggleSlice.actions;

export default toggleSlice.reducer;