
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { IoSearchSharp } from "react-icons/io5";


const SearchFriendModule = ()=> {
  const style = setupStyles("circlemenu")


      return (     
     
          <Link title="Поиск"  className={style.item_menu_a} to={"/app/friends/search/allusers"}><IoSearchSharp/> </Link>
      
      );
    }
    export default SearchFriendModule;