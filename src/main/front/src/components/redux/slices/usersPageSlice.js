import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { instanceWidthCred } from "../../auth/api/instance";

const initialState = {
   firstName: "",
   lastName:"",
   lastTimeOnline:"",
   avatarList: [],
   mainPageModules:[],
   promoList: [],
   infoList: [],
   actualList: [],
   friendsList: [],
   subscriberList: [],
   subscribtionsList: [],
};

const PATH = 'app/friends/'

export const getUsersData = createAsyncThunk(

    "usersPageSlice/getUsersData",
    async (data, { rejectWithValue }) => {
        try {
            const response = await instanceWidthCred.post(PATH + "getusersdata", {
                userId: data,
            });
            return response.data.UserData;
        } catch (error) {
            console.log(error.response.data);
            return rejectWithValue(error.response.data);
        }
    }
);



const usersPageSlice = createSlice({
    name: "usersPageSlice",
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
        builder.addCase(getUsersData.pending, (state, action) => {
            return {
                ...state,
            };
        });
        builder.addCase(getUsersData.fulfilled, (state, action) => {
            return {
                ...state,
                userId: action.payload.userId,
                firstName: action.payload.firstName,
                lastName:action.payload.lastName,
                lastTimeOnline:action.payload.lastTimeOnline,
                avatarList: action.payload.avatar,
                mainPageModules:action.payload.listModulesMainPage,
                promoList: action.payload.imagePromos,
                infoList: action.payload.infoModules,
                actualList: action.payload.momentsList,
                friendsList: action.payload.userFriends,
                subscriberList: action.payload.subscribers,
                subscribtionsList: action.payload.subscriptions,
            };
        });
        builder.addCase(getUsersData.rejected, (state, action) => {
            return {
                ...state,
            };
        });
    },

});



export const { startMoment, endMoment} = usersPageSlice.actions;

export default usersPageSlice.reducer;