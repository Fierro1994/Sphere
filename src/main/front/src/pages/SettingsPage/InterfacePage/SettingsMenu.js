import React from "react";
import { Checkbox } from "./CheckBox";
import setupStyles from "../../stylesModules/setupStyles";

const SettingsMenu = () => {
    const style = setupStyles("mainstyle")
    const style2 = setupStyles("settingstyle")
    return (
        <div className={style.tablemain}>
            <div className={style2.menu}>
                <div className={style2.group_settings}>
                    <h2>Элементы главного меню</h2>
                <Checkbox  />  
                </div>
            </div>
   </div>
    );
}

export default SettingsMenu; 

