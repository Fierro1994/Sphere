import React from "react";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { Link } from "react-router-dom";


const GaleryModule = ()=> {
  const style = setupStyles("circlemenu")



      return (
              
        <Link  className={style.item_menu_a} to={"/app/gallery/photo"}>Галерея</Link>

      );
    }
    export default GaleryModule;