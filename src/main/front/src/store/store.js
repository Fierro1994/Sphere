import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../components/redux/slices/authSlice";
import mainmenuReducer from "../components/redux/slices/mainMenuSlice";
import mainpagemoduleReducer from "../components/redux/slices/mainPageModuleSlice";
import galleryReducer from "../components/redux/slices/galerySlice";
import getToggle from "../components/view/toggleSlice";
import promoReducer from "../components/redux/slices/promoSlice"
import headerReducer from "../components/redux/slices/headerSlice"
import infoMpReducer from "../components/redux/slices/infoMpSlice"
import momentsReducer from "../components/redux/slices/momentsSlice"
import friendsReducer from "../components/redux/slices/friendsSlice"
import onlineReducer from "../components/redux/slices/userOnlineSlice"
export const store = configureStore({
  reducer: {
    auth: authReducer,
    gallery: galleryReducer,
    mainmenu: mainmenuReducer,
    mainpagemodule: mainpagemoduleReducer,
    toggle: getToggle,
    promo: promoReducer,
    header: headerReducer,
    infomp:infoMpReducer,
    moments: momentsReducer,
    friendsred: friendsReducer,
    onlinered: onlineReducer
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['auth/getItemsMenu/fulfilled',"file/updateImagePromoList"],
        ignoredActionPaths: ['meta.arg', 'payload.timestamp',"payload.headers", `payload.config`, `payload.request`],
        ignoredPaths: ['auth.listMenuItems', "file.updateImagePromoList"],
      },
    }),
});

export default store;