import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import Content from "./Content";
import setupStyles from "../stylesModules/setupStyles";
import MainAvatarViewer from "../../components/menu_comp/MainAvatarViewer";

const ProfilePage = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);

  return (
    <>
        <div className={style.container}>
          <div className={style2.menu_items}>
            <MainAvatarViewer nameModule={"menuModules"} namePage={"ProfileModule"} showSet={true} />
          </div>
          <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
            <Content />
          </div>
        </div> 
    </>
  );
}
export default ProfilePage;
