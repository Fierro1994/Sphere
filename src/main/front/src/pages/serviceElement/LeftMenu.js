import React, { useState, useEffect, useMemo } from "react";
import setupStyles from "../stylesModules/setupStyles";
import { useDispatch, useSelector } from 'react-redux';
import { logoutUser } from '../../components/redux/slices/authSlice';

import { useNavigate } from "react-router-dom";

import menuModuleChange from "./menuModuleChange";

function LeftMenu(nameModule, namePage) {
  const navigate = useNavigate()
  const style = setupStyles("circlemenu")
  const dispatch = useDispatch();
  const toggleSlice = useSelector((state) => state.toggle);
  const handleSubmit = (e) => {
    e.preventDefault();
    navigate("/")
    dispatch(logoutUser());
  };
  let listModuleName = menuModuleChange(nameModule)
  
  const result = listModuleName.map((element, i) => {
    var l = listModuleName.length

    if (element.type.name === namePage) {
      
      return <span className={style.small_circle_this} key={i} style={{
        top: i*6 + "vw",
      }}>{element}</span>
    }

    return <span className={style.small_circle} key={i} style={{
      top: i*6 + "vw",
    }}>{element}</span>

  });
  return (
    <>
         {result}
    </>

  )
}

export default LeftMenu;



