
import React, { useState } from "react";
import { Link } from "react-router-dom";
import setupStyles from "../../../stylesModules/setupStyles";
import { ImProfile } from "react-icons/im";


const KonstruktModuleProfile = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Конструктор профиля"  className={style.item_menu_a} to={"/app/settings/konstrukt/profile"}><ImProfile/> </Link>
      
      );
    }
    export default KonstruktModuleProfile;