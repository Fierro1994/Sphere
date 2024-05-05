import React, { useState, useEffect, useMemo } from "react";
import setupStyles from "../stylesModules/setupStyles";
import { useDispatch, useSelector } from 'react-redux';
import { logoutUser } from '../../components/redux/slices/authSlice';

import { useNavigate } from "react-router-dom";

import menuModuleChange from "./menuModuleChange";

function CircleMenuItems(nameModule, namePage) {
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
    if (element.type.name === "LogoutModule") {
      return <span className={style.logout} onClick={handleSubmit} key={i} style={{
        left: (50 - 90 * Math.cos(-0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%",
        top: (50 + 90 * Math.sin(-0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%"
      }}>{element}</span>
    }
    if (element.type.name === namePage) {
      
      return <span className={style.thisPage} key={i} style={{
        left: (50 - 90 * Math.cos(-0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%",
        top: (50 + 90 * Math.sin(-0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%"
      }}>{element}</span>
    }

    return <span className={style.span_menu} key={i} style={{
      left: (50 - 90 * Math.cos(-0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%",
      top: (50 + 90 * Math.sin(-0.5 * Math.PI - 2 * (1 / l) * i * Math.PI)).toFixed(4) + "%"
    }}>{element}</span>

  });
  return (
    <>
      <div className={!toggleSlice.toggle ? style.item : style.item + " " + style.open}>
          <div className={style.item_style}>{result}</div>
        </div>
    </>

  )
}

export default CircleMenuItems;



