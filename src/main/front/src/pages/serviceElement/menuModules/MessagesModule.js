import React from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { Link } from "react-router-dom";


const MessagesModule = ()=> {
  const style = setupStyles("circlemenu")



      return (
              
        <Link  className={style.item_menu_a} to={"/app/messages"}>Сообщения</Link>

      );
    }
    export default MessagesModule;