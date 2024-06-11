import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import MainAvatarViewer from "../../../components/menu_comp/MainAvatarViewer";
import LeftMenuViewer from "../../../components/menu_comp/LeftMenuViewer";
import setupStyles from "../../stylesModules/setupStyles";
import SearchFriendView from "./SearchFriendView";


const SearchFrPage = () => {
  const mainmenu = useSelector((state) => state.mainmenu);
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
        <LeftMenuViewer nameModule={"friendsModule"} namePage={"SearchFriendModule"} showSet={true} />
      </div>
      <div className={style.container_content}>
        <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
          < div className={style.tablemain}>
            <SearchFriendView/>
          </div>
         
        </div>
      </div>
    </div>

  );
}

export default SearchFrPage; 
