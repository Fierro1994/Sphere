import { useEffect, useState } from "react"
import setupStyles from "../../stylesModules/setupStyles"
import { FaPencil, FaRegTrashCan } from "react-icons/fa6"
import MainPageSetInfo from "./MainPageSetInfo"
import avatar from "../../../assets/avatar_mainpage.jpg"

const MainPageSetHeader = () => {
   const style = setupStyles("mainpagesetstyle")
   var listMenuModules = []
   if (localStorage.getItem("mainPageModules")) {
      listMenuModules = JSON.parse(localStorage.getItem("mainPageModules"))
   }
   const [enabled, setEnabled] = useState(false)
   const [show, setShow] = useState(false)
   useEffect(() => {
      listMenuModules.map((element, i) => {
         if (element.name === "HEADER" && element.isEnabled === true) {
            setEnabled(true)
         }

      })
   }, []);
   return (
      <>
         
         <div className={!show ? style.popup_header : style.popup_header + " " + style.open}>
            <p>настройки фона</p>
            <a className={style.popup_a}></a>
            <div>
            </div>
         </div>
         <div className={style.header}>
         <MainPageSetInfo/>
            
            <div className={style.header_main}>

            <div className={style.header_button_container}><button className={style.correct} onClick={function () {
                        setShow(!show)
                     }}> <img src={avatar}></img> изменить </button></div> 
            </div>
            <MainPageSetInfo/>
         </div>

      </>

   );
}

export default MainPageSetHeader; 
