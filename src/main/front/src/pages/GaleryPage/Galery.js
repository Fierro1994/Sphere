import React from "react";
import setupStyles from "../stylesModules/setupStyles";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../serviceElement/MainAvatarViewer";
import GaleryView from "./GaleryView";
import LeftMenuViewer from "../serviceElement/LeftMenuViewer";

const Galery = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <div>
      <div className={style.container}>
        <div className={style2.menu_items}>
          <MainAvatarViewer nameModule={"menuModules"} namePage={"GaleryModule"} showSet={true} />
          <LeftMenuViewer nameModule={"galleryModule"} namePage={"GalleryModulePhoto"} showSet={true} />

        </div>
        <div className={style.container_content}>

          <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
            <div className={style.tablemain}>
              <GaleryView />
            </div>

          </div>
        </div>
      </div>

    </div>


  );
}
export default Galery;
