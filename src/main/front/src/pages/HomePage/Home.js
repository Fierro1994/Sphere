import React, { useEffect } from "react";
import Login from "../../components/auth/Login/Login";
import { useSelector } from "react-redux";
import Content from "./Content";
import setupStyles from "../stylesModules/setupStyles";
import styledefault from "../stylesModules/BlackTheme/mainstyle.module.css";
import MainAvatarViewer from "../../components/menu_comp/MainAvatarViewer";

const Home = () => {
  const auth = useSelector((state) => state.auth);
  const style = setupStyles("mainstyle")
  const style2 = setupStyles("circlemenu")
  const toggleSlice = useSelector((state) => state.toggle);
  return (
    <>
      {auth._id ?
        <div className={style.container}>
          <div className={style2.menu_items}>
            <MainAvatarViewer nameModule={"menuModules"} namePage={"MainModule"} showSet={true} />
            :
          </div>
          <div className={toggleSlice.toggle ? style.containerContent : style.containerContent + " " + style.containerContentClose}>
            <Content />
          </div>
        </div> :
        <div className={styledefault.content}>
          <div className={styledefault.main}>
            <h2>Добро пожаловать</h2>
            <Login /></div></div>}
    </>
  );
}
export default Home;
