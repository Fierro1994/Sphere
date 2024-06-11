import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../components/menu_comp/MainAvatarViewer";
import setupStyles from "../stylesModules/setupStyles";
import UsersPageView from "./UsersPageView";
import { useParams, useSearchParams } from "react-router-dom";


const UsersPage = () => {
  const mainmenu = useSelector((state) => state.mainmenu);
  const params = useParams();
  const [result, setResult] = useState("")
  useEffect(() => {
    setResult(<MainAvatarViewer nameModule={"menuModules"} namePage={"FriendsModule"} showSet={true} />)
  }, [mainmenu]);
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);

  return (
    <div className={style.container}>
      <div className={style2.menu_items}>
        {result}
      </div>
      <div className={style.container_content}>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          < div className={style.tablemain}>
            <UsersPageView id={params.id}/>
          </div>
         
        </div>
      </div>
    </div>

  );
}

export default UsersPage; 
