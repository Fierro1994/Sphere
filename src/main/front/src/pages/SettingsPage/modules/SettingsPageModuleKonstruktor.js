
import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { getMainPageModules } from "../../../components/redux/slices/authSlice";
import { IoConstruct } from "react-icons/io5";

const SettingsPageModuleKonstruktor = ()=> {
    const style = setupStyles("circlemenu")

      return (     
        <Link title="Конструктор" className={style.item_menu_a} to={"/app/settings/mainpage"}><IoConstruct/></Link>
      );
    }
    export default SettingsPageModuleKonstruktor;