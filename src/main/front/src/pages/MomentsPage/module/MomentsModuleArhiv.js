import React, { useState } from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { Link } from "react-router-dom";


const MomentsModuleArhiv = ()=> {
  const style = setupStyles("circlemenu")
      return (     
        <Link className={style.item_menu_a} to={"/app/moments/arhiv"}>Архив</Link>

      );
    }
    export default MomentsModuleArhiv;