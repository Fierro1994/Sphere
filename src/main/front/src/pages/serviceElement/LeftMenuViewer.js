import React, { useState } from "react";
import setupStyles from "../stylesModules/setupStyles";
import { useDispatch, useSelector } from 'react-redux';
import { setSlice } from '../../components/view/toggleSlice';
import CircleMenuItems from "./CircleMenuItems";
import LeftMenu from "./LeftMenu";

const LeftMenuViewer = ({ nameModule, namePage, showSet }) => {
  const style = setupStyles("circlemenu")
  const [show, setShow] = useState(showSet)
  const toggleSlice = useSelector((state) => state.toggle);

  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const srcValue = "data:image/png;base64, " + auth.avatar
  const moduleMenu = LeftMenu(nameModule, namePage)

  const onAvatarClick = () => {
    dispatch(setSlice(!show))
    setShow(!show)
  }
  return (
    <div className={style.left_menu_container}>
      <div className={!toggleSlice.toggle ? style.item : style.item + " " + style.open}>
       <div className={style.left_menu_flex}>
        {moduleMenu}
      </div>
      </div>
    </div>
  );
}
export default LeftMenuViewer;
