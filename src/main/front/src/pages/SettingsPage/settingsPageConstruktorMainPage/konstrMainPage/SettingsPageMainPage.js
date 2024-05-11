import React from "react";
import setupStyles from "../../../stylesModules/setupStyles";
import MainPageSetHeader from "./MainPageSetHeader";
import MainPageSetNav from "./MainPageSetNav";
import MainPageSetPromo from "./MainPageSetPromo";
import MainPageSetContent from "./MainPageSetContent";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../../components/menu_comp/LeftMenuViewer";
import MainPageSetActual from "./MainPageSetActual";


const SettingsPageMainPage = () => {
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
            <h1>Главная страница</h1>
          </div>
          <div>
            <MainPageSetPromo />
            <MainPageSetHeader />
            <MainPageSetActual />
            <MainPageSetNav />
            <MainPageSetContent />
          </div>
        </div>
      </div>
    </div>

  );
}

export default SettingsPageMainPage; 
