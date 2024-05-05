import React from "react";
import { useSelector } from "react-redux";
import setupStyles from "../../stylesModules/setupStyles";
import MainAvatarViewer from "../../serviceElement/MainAvatarViewer";
import Moments from "../moments/Moments";
import LeftMenuViewer from "../../serviceElement/LeftMenuViewer";

const ArhivPage = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <div>
      <div className={style.container}>
        <div className={style2.menu_items}>
        <MainAvatarViewer nameModule={"menuModules"} namePage={"MomentsModule"} showSet={true} />        </div>
        <LeftMenuViewer nameModule={"momentsPage"} namePage={"MomentsModuleArhiv"} showSet={true} />

        <div className={style.container_content}>

          <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
            <div className={style.h1style}>
              <h1>Архив
              </h1>
              <div className={style.tablemain}>

                <Moments />

              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

  );
}

export default ArhivPage; 
