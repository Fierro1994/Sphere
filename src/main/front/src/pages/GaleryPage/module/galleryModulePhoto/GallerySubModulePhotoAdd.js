
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../../stylesModules/setupStyles";

const GallerySubModulePhotoAdd = ()=> {
    const auth = useSelector((state) => state.auth);
    const style = setupStyles("circlemenu")


      return (     
   <Link  className={style.item_menu_a} to={"/app/gallery/photo"}>Добавить</Link>
      );
    }
    export default GallerySubModulePhotoAdd;