import React, { useEffect, useState } from "react";
import SettingsMenu from "./SettingsMenu";
import setupStyles from "../../stylesModules/setupStyles";
import { useSelector } from "react-redux";
import Interface from "./Interface";
import MainAvatarViewer from "../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../components/menu_comp/LeftMenuViewer";


const SettingsPageInterface = () => {
  const mainmenu = useSelector((state) => state.mainmenu);
  const [result, setResult] = useState("")
  useEffect(() => {
    setResult(<MainAvatarViewer nameModule={"menuModules"} namePage={"SettingsModules"} showSet={true} />)
  }, [mainmenu]);
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <div className={style.container}>
      <div className={style2.menu_items}>
        {result}
        <LeftMenuViewer nameModule={"settingsInterface"} namePage={"SettingsPageModuleInterface"} showSet={true} />
      </div>
      <div className={style.container_content}>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          <div className={style.h1style}>
            <h1>Настройки внешнего вида</h1>
          </div>
          <div >
            <Interface />
          </div>
          <div>
            <SettingsMenu />
          </div>
        </div>
      </div>
    </div>

  );
}

export default SettingsPageInterface; 
