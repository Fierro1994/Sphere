import React from "react";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";

const GalleryModuleArhiv = () => {
  const style = setupStyles("circlemenu")
      return (     
        <Link  className={style.item_menu_a} to={"/app/gallery/arhiv"}>Архив</Link>
      );
};
    export default GalleryModuleArhiv;