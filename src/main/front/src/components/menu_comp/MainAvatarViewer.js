import React, {useEffect, useState} from "react";
import setupStyles from "../../pages/stylesModules/setupStyles";
import {useDispatch, useSelector} from 'react-redux';
import {setSlice} from '../../components/view/toggleSlice';
import CircleMenuItems from "./CircleMenuItems";
import defAvatar from "../../assets/defavatar.jpg"
import {updateHeader} from "../redux/slices/avatarSlice";

const MainAvatarViewer = ({nameModule, namePage, showSet}) => {
    const style = setupStyles("circlemenu")
    const [show, setShow] = useState(showSet)
    const dispatch = useDispatch();
    const auth = useSelector((state) => state.auth);
    const header = useSelector((state) => state.header);
    const moduleMenu = CircleMenuItems(nameModule, namePage)
    useEffect(() => {
        if (header.isUpdFull === false) {
            dispatch(updateHeader(auth._id));
        }
    }, [header.isUpdFull]);
    const onAvatarClick = () => {
        dispatch(setSlice(!show))
        setShow(!show)
    }
    return (
        <div className={style.mainAvatarCircle}>
            {header.headerList && header.headerList.length > 0 ?
                <img className={style.open_menu} src={header.headerList[header.headerList.length - 1]}
                     onClick={function () {
                         onAvatarClick()
                     }
                     }/> : <img className={style.open_menu} src={defAvatar} onClick={function () {
                    onAvatarClick()
                }
                }/>}
            <div className={style.circle}>
                {moduleMenu}
            </div>
            <div className={style.info_contain}>
                <div className={style.timeOnline}>
                    <p>{
                    auth._id ? "в сети" :
                        "Был в сети" + " " + auth.onlineTime}</p></div>
                <div className={style.profilename}>
                    <p>{auth.firstName + " " + auth.lastName}</p>
                </div>
            </div>
        </div>
    );
}
export default MainAvatarViewer;