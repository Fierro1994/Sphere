
import React, { useState } from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { Link } from "react-router-dom";


const ProfileModule = ()=> {

  const style = setupStyles("circlemenu")

      return (     
        <Link  className={style.item_menu_a} to={"/app/profile"}>Профиль</Link>

      );
    }
    export default ProfileModule;