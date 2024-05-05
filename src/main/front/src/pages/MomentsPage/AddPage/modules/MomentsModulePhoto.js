
import React, { useState } from "react";
import { MdPhotoCamera } from "react-icons/md";
import setupStyles from "../../../stylesModules/setupStyles";

const MomentsModulePhoto = ()=> {

  const style = setupStyles("circlemenu")

      return (     
        <div className={style.item_menu_a} to={""}><MdPhotoCamera className={style.icons}/></div>
      );
    }
    export default MomentsModulePhoto;