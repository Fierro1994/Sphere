import React, { useEffect } from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../components/menu_comp/LeftMenuViewer";


const SecurityPage = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <div className={style.container}>
      <div className={style2.menu_items}>
        <MainAvatarViewer nameModule={"menuModules"} namePage={"SettingsModules"} showSet={true} />
        <LeftMenuViewer nameModule={"settingsInterface"} namePage={"SettingsPageModuleSecurity"} showSet={true} />
      </div>
      <div className={style.container_content}>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <div className={style.h1style}>
            <h1>Настройки безопасности</h1>
          </div>
          <div >
          </div>
          <div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SecurityPage; 
