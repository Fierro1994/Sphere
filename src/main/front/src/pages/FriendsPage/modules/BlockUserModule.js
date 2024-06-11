
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { TbLock } from "react-icons/tb";


const BlockUserModule = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Заблокированные"  className={style.item_menu_a} to={"/app/block/"}><TbLock/> </Link>
      
      );
    }
    export default BlockUserModule;