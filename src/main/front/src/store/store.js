import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../components/redux/slices/authSlice";
import mainmenuReducer from "../components/redux/slices/mainMenuSlice";
import mainpagemoduleReducer from "../components/redux/slices/mainPageModuleSlice";
import galleryReducer from "../components/redux/slices/galerySlice";
import getToggle from "../components/view/toggleSlice";
export const store = configureStore({
  reducer: {
    auth: authReducer,
    gallery: galleryReducer,
    mainmenu: mainmenuReducer,
    mainpagemodule: mainpagemoduleReducer,
    toggle: getToggle
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