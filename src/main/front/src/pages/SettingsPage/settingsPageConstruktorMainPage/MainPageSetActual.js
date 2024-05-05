import { useEffect, useState } from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { FaPencil, FaRegTrashCan } from "react-icons/fa6";
import actualImg from "../../../assets/actual.jpg";


const MainPageSetActual = () => {
   const style = setupStyles("mainpagesetstyle")
   var listMenuModules = []
   if (localStorage.getItem("mainPageModules")) {
      listMenuModules = JSON.parse(localStorage.getItem("mainPageModules"))
    }
    const [enabled, setEnabled] = useState(false) 
    const [show, setShow] = useState(false) 
    useEffect(() => {
      listMenuModules.map((element, i) => {
         if(element.name === "ACTUAL" && element.isEnabled === true)
         {
            setEnabled(true)
         }
         
      })
    }, []);
    return (
      <>
      <div className={style.actual}>
         <div className={style.actual_circle}><img src={actualImg}></img></div>
         <div className={style.actual_circle}><img src={actualImg}></img></div>
         <div className={style.actual_circle}><img src={actualImg}></img></div>
      <div className={style.actual_controls}>
               </div>
               <div className={style.actual_circle}><img src={actualImg}></img></div>
         <div className={style.actual_circle}><img src={actualImg}></img></div>
         <div className={style.actual_circle}><img src={actualImg}></img></div>
      </div>
      </>

         );
}
 
export default MainPageSetActual; 
