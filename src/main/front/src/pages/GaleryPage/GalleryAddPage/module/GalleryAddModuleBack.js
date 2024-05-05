import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../../stylesModules/setupStyles";

const GalleryAddModuleBack = ()=> {
    const auth = useSelector((state) => state.auth);
    const style = setupStyles("mainstyle")

      return (     
        <Link  className={style.item_menu_a} to={"/app/gallery/photo"}>Назад</Link>
      );
    }
    export default GalleryAddModuleBack;