
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { IoIosArrowDown } from "react-icons/io";

const SubscribeUsersModule = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Подписчики"  className={style.item_menu_a} to={"/app/friends/subscribe"}><IoIosArrowDown/> </Link>
      
      );
    }
    export default SubscribeUsersModule;