import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/instance";


const initialState = {
  listMenuItems: [],
};

export const updateSelectedMenu = createAsyncThunk(
  "mainmenu/updateSelectedMenu",
  async (data,{ rejectWithValue }) => {
   
    try {
      const response = await instanceWidthCred.post("/api/profile/settings/updatemenuelement", {
        userId: data.get("userId"),
        name: data.getAll("name")
      });
     
      const listItems = []
      response.data.body.menu.forEach( (item) =>{
        listItems.push({id: item.id, name:item.name,isEnabled: item.isEnabled, nametwo:item.nametwo})     
     })
     localStorage.setItem("menuModules", JSON.stringify(listItems))
    return listItems;
    } catch (error) {
      console.log(error.response.data);
      return rejectWithValue(error.response.data);
    }
  }
);


const mainMenuSlice = createSlice({
  name: "mainmenu",
  initialState,
  reducers: {
  },
  extraReducers: (builder) => {
   builder.addCase(updateSelectedMenu.fulfilled, (state, action) => {
      return {...state,
        listMenuItems: action.payload
      };
  });
  },
});



export const { } = mainMenuSlice.actions;

export default mainMenuSlice.reducer;