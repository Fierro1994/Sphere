
import React, { useState } from "react";
import { useSelector } from "react-redux";
import setupStyles from "../../../stylesModules/setupStyles";
import { Link } from "react-router-dom";

const MomentsModuleVideo = ()=> {
  
  const style = setupStyles("circlemenu")


      return (     
        <Link  className={style.item_menu_a} to={"/app/moments"}>Видео</Link>
      );
    }
    export default MomentsModuleVideo;