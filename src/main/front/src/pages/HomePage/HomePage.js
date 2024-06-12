import React from "react";
import HomePageView from "./HomePageView";
import { useSelector } from "react-redux";
import setupStyles from "../stylesModules/setupStyles";
import MainAvatarViewer from "../../components/menu_comp/MainAvatarViewer";


const HomePage = () => {

  const toggleSlice = useSelector((state) => state.toggle);
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  return (
    <div className={style.container}>
      <div className={style2.menu_items}>
        <MainAvatarViewer nameModule={"menuModules"} namePage={"MainModule"} showSet={true} />
        :
      </div>
      <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
        <HomePageView />
      </div>
    </div>
  );
}

export default HomePage;
