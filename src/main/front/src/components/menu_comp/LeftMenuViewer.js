import React from "react";
import setupStyles from "../../pages/stylesModules/setupStyles";
import {useSelector } from 'react-redux';
import LeftMenu from "./LeftMenu";

const LeftMenuViewer = ({ nameModule, namePage, showSet }) => {
  const style = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  const moduleMenu = LeftMenu(nameModule, namePage)
  return (
    <div className={style.left_menu_container}>
      <div className={!toggleSlice.toggle ? style.item_small : style.item_small + " " + style.open}>
       <div className={style.left_menu_flex}>
        {moduleMenu}
      </div>
      </div>
    </div>
  );
}
export default LeftMenuViewer;
