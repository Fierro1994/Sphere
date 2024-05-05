
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { LuAppWindow } from "react-icons/lu";


const SettingsPageModuleInterface = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Интерфейс"  className={style.item_menu_a} to={"/app/settings/interface"}><LuAppWindow/> </Link>
      
      );
    }
    export default SettingsPageModuleInterface;