import React from "react";
import { useSelector } from "react-redux";
import setupStyles from "../../../stylesModules/setupStyles";
import MainAvatarViewer from "../../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../../components/menu_comp/LeftMenuViewer";


const KonstruktProfilePage = () => {
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <div className={style.container}>
      <div className={style2.menu_items}>
        <MainAvatarViewer nameModule={"menuModules"} namePage={"SettingsModules"} showSet={true} />
        <LeftMenuViewer nameModule={"settingsConstruktor"} namePage={"SettingsPageModuleKonstruktor"} showSet={true} />
      </div>
      <div className={style.container_content}>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <div className={style.h1style}>
            <h1>Конструктор профиля</h1>
          </div>
          <div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default KonstruktProfilePage; 
