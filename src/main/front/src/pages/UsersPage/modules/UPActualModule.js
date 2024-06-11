import { useEffect, useState } from "react";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { useDispatch, useSelector } from "react-redux";
import { MdOutlineClose } from "react-icons/md";
import { GrFormNext, GrFormPrevious } from "react-icons/gr";
import UPActualPlayer from "../service/UPActualPlayer";

const UPActualModule = () => {
   const style = setupStyles("actualMP")
   const [popShow, setPopShow] = useState(false)
   const userPage = useSelector((state) => state.userspages);
   const [showCtrl, setShowCtrl] = useState(false)
   const [actV, setActV] = useState(null)
   const [showPlayer, setShowPlayer] = useState(true)
   const dispatch = useDispatch()


   useEffect(() => {
      setActV(0);
   }, []);

   useEffect(() => {
      if (userPage.isEndMoments) {
         setActV(actV === userPage.actualList.length - 1 ? 0 : actV + 1);
         setShowCtrl(false)
      }
   }, [userPage?.isEndMoments]);

   useEffect(() => {
      const timeoutId = setTimeout(() => {
         setShowCtrl(false)
      }, 3000);
      return () => clearTimeout(timeoutId);
   }, [showCtrl]);


   const nextAct = () => {
      setActV(actV === userPage.actualList.length - 1 ? 0 : actV + 1);
      setShowCtrl(false)
   };
   const prevAct = () => {
      setActV(actV === 0 ? userPage.actualList.length - 1 : actV - 1);
      setShowCtrl(false)
   };

   return (
      <>
         <div className={style.actual}>
            <div className={style.actual_circle} onClick={e => {
               setPopShow(true)
               setShowPlayer(true)
            }}></div>
            <div className={!popShow ? style.pop_cont : style.pop_cont + " " + style.open}>
               {showPlayer && <div className={style.actual_show}>
                  <div className={style.crop_container_cropped}>
                     <span className={style.circle_act_items}>{userPage.actualList.map((element, i) => {
                        return (
                           <span key={i} onClick={() => setActV(i)} className={actV !== i ? style.circle_items : style.circle_items_this}>

                           </span>
                        )
                     })}</span>
                     {userPage.actualList.map((element, i) => {
                        return (
                           <span key={i}>
                              {actV === i && <UPActualPlayer videosrc={element.key + "." + element.format} userId={userPage.userId} />}
                           </span>
                        )
                     })}
                  </div>
               </div>}
               {showPlayer && <span className={style.arrow_prev_cont} onClick={prevAct} ><GrFormPrevious className={style.arrow_prev} />   </span>}
               {showPlayer && <span className={style.arrow_next_cont} onClick={nextAct} ><GrFormNext className={style.arrow_next} /> </span>}
               {popShow && <MdOutlineClose className={style.close_svg} onClick={(e) => {
                  e.preventDefault()
                  setPopShow(false)
                  setShowCtrl(false)
                  setShowPlayer(false)
                  setActV(0)
               }} />}
            </div>
         </div>
      </>

   );
}

export default UPActualModule;






