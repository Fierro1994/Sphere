
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { IoIosArrowUp } from "react-icons/io";

const SubscriptionsModule = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Подписки"  className={style.item_menu_a} to={"/app/friends/subscriptions"}><IoIosArrowUp/> </Link>
      
      );
    }
    export default SubscriptionsModule;