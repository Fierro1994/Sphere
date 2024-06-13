import {createSlice, createAsyncThunk} from "@reduxjs/toolkit";
import {instanceWidthCred} from "../../auth/api/instance";

const initialState = {
    friendsList: [],
    searchFrList: [],
    subscriptionsList: [],
    subscribersList: [],
    friendAvatars: []
};

const PATH = '/friends/'


export const getFriendsList = createAsyncThunk(
    "friendsSlice/getFriendsList",
    async (data, {rejectWithValue}) => {
        try {
            const response = await instanceWidthCred.get(PATH + "getfriendslist");
            return response.data.Friends;
        } catch (error) {
            console.log(error.response.data);
            return rejectWithValue(error.response.data);
        }
    }
);


export const searchFriends = createAsyncThunk(
    "friendsSlice/searchFriends",
    async (data, {rejectWithValue}) => {
        try {
            const response = await instanceWidthCred.get(PATH + "search/allusers");
            return response.data.body;
        } catch (error) {
            console.log(error.response.data);
            return rejectWithValue(error.response.data);
        }
    }
);


export const addFriendSubscribe = createAsyncThunk(
    "friendsSlice/addFriendSubscribe",
    async (data, {rejectWithValue}) => {
        try {
            const response = await instanceWidthCred.post(PATH + "subscribe", {
                "requestor": data.get("user"),
                "target": data.get("targetUser")
            });
            return response.data.body;
        } catch (error) {
            console.log(error.response.data);
            return rejectWithValue(error.response.data);
        }
    }
);
export const getFriendSubscribe = createAsyncThunk(
    "friendsSlice/getFriendSubscribe",
    async (data, {rejectWithValue}) => {

        try {
            const response = await instanceWidthCred.get(PATH + "getsubscriberlist", {});
            return response.data.Subscribers;
        } catch (error) {
            console.log(error.response.data);
            return rejectWithValue(error.response.data);
        }
    }
);

export const getFriendSubscriptions = createAsyncThunk(
    "friendsSlice/getFriendSubscriptions",
    async (data, {rejectWithValue}) => {
        try {
            const response = await instanceWidthCred.get(PATH + "getsubscriptionslist", {});
            return response.data.Subscriptions;
        } catch (error) {
            console.log(error.response.data);
            return rejectWithValue(error.response.data);
        }
    }
);


const friendsSlice = createSlice({
    name: "friendsSlice",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder.addCase(getFriendsList.pending, (state, action) => {
            return {
                ...state,

            };
        });
        builder.addCase(getFriendsList.fulfilled, (state, action) => {
            return {
                ...state,
                friendsList: action.payload,


            };
        });
        builder.addCase(getFriendsList.rejected, (state, action) => {
            return {
                ...state,
            };
        });

        builder.addCase(searchFriends.pending, (state, action) => {
            return {
                ...state,

            };
        });
        builder.addCase(searchFriends.fulfilled, (state, action) => {
            return {
                ...state,
                searchFrList: action.payload,
            };
        });
        builder.addCase(searchFriends.rejected, (state, action) => {
            return {
                ...state,
            };
        });
        builder.addCase(getFriendSubscribe.pending, (state, action) => {
            return {
                ...state,

            };
        });
        builder.addCase(getFriendSubscribe.fulfilled, (state, action) => {
            return {
                ...state,
                subscribersList: action.payload,
            };
        });
        builder.addCase(getFriendSubscribe.rejected, (state, action) => {
            return {
                ...state,
            };
        });
        builder.addCase(getFriendSubscriptions.pending, (state, action) => {
            return {
                ...state,

            };
        });
        builder.addCase(getFriendSubscriptions.fulfilled, (state, action) => {
            return {
                ...state,
                subscriptionsList: action.payload,
            };
        });
        builder.addCase(getFriendSubscriptions.rejected, (state, action) => {
            return {
                ...state,
            };
        });
    },

});


export const {} = friendsSlice.actions;

export default friendsSlice.reducer;