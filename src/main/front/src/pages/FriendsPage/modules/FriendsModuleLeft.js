
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { FaUserFriends } from "react-icons/fa";


const FriendsModuleLeft = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Друзья"  className={style.item_menu_a} to={"/app/friends/"}><FaUserFriends/> </Link>
      
      );
    }
    export default FriendsModuleLeft;