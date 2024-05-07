import React from "react";
import { useSelector } from "react-redux";
import setupStyles from "../stylesModules/setupStyles";
import MainAvatarViewer from "../../components/menu_comp/MainAvatarViewer";
import FriendsView from "./FriendsView";

const FriendsPage = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <>
      <div className={style.container}>
        <div className={style2.menu_items}>
          <MainAvatarViewer nameModule={"menuModules"} namePage={"FriendsModule"} showSet={true} />
        </div>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <FriendsView />
        </div>
      </div>
    </>
  );
}
export default FriendsPage;
