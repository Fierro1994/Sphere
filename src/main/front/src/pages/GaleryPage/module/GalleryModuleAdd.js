import React from "react";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";

const GalleryModuleAdd = () => {
  const style = setupStyles("circlemenu")
 return (     
        <Link  className={style.item_menu_a} to={"/app/gallery/add"}>Добавить</Link>
      );
};
    export default GalleryModuleAdd;