import { Link } from "react-router-dom";
import setupStyles from "../../../pages/stylesModules/setupStyles";
const SettingsModules = ()=> {
   const style = setupStyles("circlemenu")

    return (     
       <Link  className={style.item_menu_a} to={"/app/settings/interface"}>Настройки</Link>
    );
  }
  export default SettingsModules;