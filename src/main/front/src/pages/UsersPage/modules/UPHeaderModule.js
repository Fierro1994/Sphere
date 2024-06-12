import { createRef, useEffect, useState } from "react"
import setupStyles from "../../../pages/stylesModules/setupStyles"
import { GrFormNext, GrFormPrevious } from "react-icons/gr";
import UPInfoModule from "./UPInfoModule";
import CommentAvatar from "../../ProfilePage/components/service/Comment_avatar";
import { useSelector } from "react-redux";

const UPHeaderModule = ({info}) => {
   const PATH = "http://localhost:3000/avatar"
  
   const userPage = useSelector((state) => state.userspages);
   const [infomod, setinfoMod] = useState(null);
   const [infomod2, setinfoMod2] = useState(null);
   const style = setupStyles("headerUP")
   const [showPopup, setShowPopup] = useState(false)
   const refSlide = createRef();
   const [slide, setSlide] = useState(0);
   const nextSlide = () => {
      setSlide(slide === userPage.avatarList.length - 1 ? 0 : slide + 1);

   };
   const prevSlide = () => {

      setSlide(slide === 0 ? userPage.avatarList.length - 1 : slide - 1);
   };

   function dateFormat(time) {
    var time_day = ""
    var day = time[2]
    var mounth = time[1]
  
    var now = new Date()
  
    if(day < 10) day = "0" + day
    if(mounth < 10) mounth = "0" + mounth
  
    if (day !== now.getDay()) {
      time_day = day + "-" + mounth + " в "
    }
    var dateFormat = "Был(а): " + time_day + " " + time[3] + ":" + time[4]
    return dateFormat
  }
  
   function result(el) {
      return PATH + "/" + userPage.userId + "/" + el
   }

   useEffect(() => {
      if(info !== null){
         setinfoMod(<UPInfoModule block = {1}/>)
         setinfoMod2(<UPInfoModule  block = {2}/>)
      }
   }, [info]);

   return (
      <>
         <div className={style.header}>
         {infomod}
            <div className={style.head_cont}>
               <div className={style.header_main}  onClick={function () {
                                  setShowPopup(!showPopup)
                              }}>


                  <div className={style.header_button_container} >
                  <img className={style.promo_img} src={PATH + "/" + userPage.userId + "/" + userPage.avatarList[0].key }  ref={refSlide} alt={"promo"}></img>
                 </div>
                 
                
               </div>
               <div className={style.users_online}>{dateFormat(userPage.lastTimeOnline)}</div>
                 <div className={style.users_name}>{userPage.firstName + " " + userPage.lastName}</div>

            </div>

            <div className={!showPopup ? style.popup_header : style.popup_header + " " + style.open}>
               <CommentAvatar/>
               <div className={style.popup_header_avatar}> 
               <>
                  <div className={style.popup_quad_shadow}>

                  {userPage.isPendDel && <div className={style.loader_container}><p className={style.loader}></p></div>}
                  </div>
                  
                  <button onClick={(e) => {
                     e.preventDefault()
                     prevSlide()
                  }} className={style.previos}><GrFormPrevious /></button>

                  <button onClick={(e) => {
                     e.preventDefault()
                     nextSlide()
                  }} className={style.next}><GrFormNext /></button>
                  {userPage.avatarList.map((element, i) => {
                     return (
                        <img className={slide === i ? style.promo_img : style.promo_img_hidden} src={result(element.key)} key={i} ref={refSlide} alt={"promo"}></img>
                     )
                     })}
</>
               

               </div>

               <div className={style.btn_line}>
                  <label className={style.exit_btn}
                     onClick={(e) => {
                        e.preventDefault()
                        setShowPopup(false)
                     }}
                  >Закрыть</label>
               </div>
            </div>
            {infomod2}
         </div>

      </>

   );
}

export default UPHeaderModule; 
