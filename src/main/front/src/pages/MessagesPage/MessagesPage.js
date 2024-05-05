import React from "react";
import Login from "../../components/auth/Login/Login";
import { useSelector } from "react-redux";
import setupStyles from "../stylesModules/setupStyles";
import styledefault from "../stylesModules/BlackTheme/mainstyle.module.css";
import MainAvatarViewer from "../serviceElement/MainAvatarViewer";
import MessagesView from "./MessagesView";

const MessagesPage = () => {
  const auth = useSelector((state) => state.auth);
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <>
        <div className={style.container}>
          <div className={style2.menu_items}>
            <MainAvatarViewer nameModule={"menuModules"} namePage={"MessagesModule"} showSet={true} />
          </div>
          <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
            <MessagesView />
          </div>
        </div> 
       
    </>
  );
}
export default MessagesPage;
