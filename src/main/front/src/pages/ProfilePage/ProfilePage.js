import React, { useState } from "react";
import MyTable from "./MyTable";
import { useSelector } from "react-redux";
import setupStyles from "../stylesModules/setupStyles";
import MainAvatarViewer from "../serviceElement/MainAvatarViewer";


const ProfilePage = () => {

  const toggleSlice = useSelector((state) => state.toggle);
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
    return (
      <div className={style.container}>
      <div className={style2.menu_items}>
        <MainAvatarViewer nameModule={"menuModules"} namePage={"ProfileModule"} showSet={true} />
        :
      </div>
      <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
        <MyTable />
      </div>
    </div>
         );
}
 
export default ProfilePage; 
