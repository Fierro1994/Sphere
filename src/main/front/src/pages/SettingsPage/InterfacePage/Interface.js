import React from "react";
import { CheckBoxInterface } from "./CheckBoxInterface";
import setupStyles from "../../stylesModules/setupStyles";

const Interface = () => {
    const style = setupStyles("mainstyle")
    const style2 = setupStyles("settingstyle")
   
    return (
        <div className={style.tablemain}>
            <div className={style2.menu}>
            <h2 className={style2.labelInterface}>Внешний вид</h2>
                <div className={style2.group_settings}>
                    
                <CheckBoxInterface  />  
                </div>
            </div>
   </div>
    );
}

export default Interface; 

