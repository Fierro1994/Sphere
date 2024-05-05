import React, { useState } from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../serviceElement/MainAvatarViewer";
import LeftMenuViewer from "../../serviceElement/LeftMenuViewer";



function VideoGaleryPage() {
  const style = setupStyles("mainstyle")
  const style3 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
 

  return (
    <div>
    <div className={style.container}>
      <div className={style3.menu_items}>
        <MainAvatarViewer nameModule={"menuModules"} namePage={"GaleryModule"} showSet={true} />
        <LeftMenuViewer nameModule={"galleryModule"} namePage={"GalleryModuleVideo"} showSet={true} />

      </div>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <div className={style.tablemain}>
       
          </div>
        </div>
    </div>
  </div>


      
  );
}

export default VideoGaleryPage;