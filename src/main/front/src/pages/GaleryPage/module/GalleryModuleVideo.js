import React from "react";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";

const GalleryModuleVideo = ()=> {
    const style = setupStyles("circlemenu")
      return (     
        <Link  className={style.item_menu_a} to={"/app/gallery/video"}>Видео</Link>
      );
    }
    export default GalleryModuleVideo;