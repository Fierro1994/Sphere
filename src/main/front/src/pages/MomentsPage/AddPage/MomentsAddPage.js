import React from "react";
import MomentsAddFunc from "./modules/MomentsAddFunc";
import setupStyles from "../../stylesModules/setupStyles";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../components/menu_comp/LeftMenuViewer";


const MomentsAddPage = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  const videoRecord = MomentsAddFunc();
  return (
    <div>
      <div className={style.container}>
        <div className={style2.menu_items}>
          <MainAvatarViewer nameModule={"menuModules"} namePage={"MomentsModule"} showSet={true} />
          <LeftMenuViewer nameModule={"momentsPage"} namePage={"MomentsModuleAdd"} showSet={true} />
        </div>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <div className={style.h1style}>
            <h1>Запись видео
            </h1>
            <div className={style.tablemain}>
              {videoRecord}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MomentsAddPage; 
