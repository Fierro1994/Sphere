import React from "react";
import EasyCrop from "./EasyCrop";
import setupStyles from "../../stylesModules/setupStyles";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../components/menu_comp/LeftMenuViewer";

function GaleryAdd() {
  const style = setupStyles("mainstyle")
  const style3 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);


  return (
    <div>
      <div className={style.container}>
        <div className={style3.menu_items}>
          <MainAvatarViewer nameModule={"menuModules"} namePage={"GaleryModule"} showSet={true} />
          <LeftMenuViewer nameModule={"galleryModule"} namePage={"GalleryModuleAdd"} showSet={true} />
        </div>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <div className={style.tablemain}>
            <EasyCrop />
          </div>
        </div>
      </div>
    </div>
  );
}

export default GaleryAdd;