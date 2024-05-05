import React from "react";
import { Link } from "react-router-dom";
import setupStyles from "../../stylesModules/setupStyles";
import { MdOutlineSecurity } from "react-icons/md";

const SettingsPageModuleSecurity = ()=> {
    const style = setupStyles("circlemenu")
      return (     
        <Link title="Безопасность" className={style.item_menu_a} to={"/app/settings/security"}><MdOutlineSecurity/></Link>
      );
    }
    export default SettingsPageModuleSecurity;