import React, { useState } from "react";
import setupStyles from "../stylesModules/setupStyles";
import { useDispatch, useSelector } from 'react-redux';
import { setSlice } from '../../components/view/toggleSlice';
import CircleMenuItems from "./CircleMenuItems";

const MainAvatarViewer = ({ nameModule, namePage, showSet }) => {
  const style = setupStyles("circlemenu")
  const [show, setShow] = useState(showSet)
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const srcValue = "data:image/png;base64, " + auth.avatar
  const moduleMenu = CircleMenuItems(nameModule, namePage)

  const onAvatarClick = () => {
    dispatch(setSlice(!show))
    setShow(!show)
  }
  return (
    <div className={style.mainAvatarCircle}>
      <img className={style.open_menu} src={srcValue} onClick={function () {
        onAvatarClick()
      }
      } />
      <div className={style.circle}>
        {moduleMenu}
      </div>
      <div className={style.timeOnline}>{
        auth._id ? "в сети" :
          "Был в сети" + " " + auth.onlineTime}</div>
      <div className={style.profilename}>
        <p>{auth.firstName + " " + auth.lastName}</p>
      </div>
    </div>
  );
}
export default MainAvatarViewer;
