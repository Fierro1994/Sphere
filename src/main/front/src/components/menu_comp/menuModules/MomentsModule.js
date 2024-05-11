
import React, { useState } from "react";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { Link } from "react-router-dom";

const MomentsModule = ()=> {

  const style = setupStyles("circlemenu")


      return (     
        <Link  className={style.item_menu_a} to={"/app/moments"}>Моменты</Link>
      );
    }
    export default MomentsModule;