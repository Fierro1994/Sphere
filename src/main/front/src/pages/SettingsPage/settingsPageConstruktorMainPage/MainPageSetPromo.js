import Carousel from 'react-bootstrap/Carousel';
import setupStyles from "../../stylesModules/setupStyles";
import { useEffect, useState } from 'react';
import { FaPencil, FaRegTrashCan } from "react-icons/fa6"
import promo1 from "../../../assets/promo_1.jpg";
import Slider from '../../../components/service/Slider';


const MainPageSetPromo = () => {
  

  
   
   


   const style = setupStyles("mainpagesetstyle")

   const [show, setShow] = useState(false)
   var listMenuModules = []
   if (localStorage.getItem("mainPageModules")) {
      listMenuModules = JSON.parse(localStorage.getItem("mainPageModules"))
   }
   const [enabled, setEnabled] = useState(false)

   useEffect(() => {
      listMenuModules.map((element, i) => {
         if (element.name === "HEADER" && element.isEnabled === true) {
            setEnabled(true)
         }

      })
   }, []);

    return (
      <>
      <Slider/>
 <div className={style.slider_container}>

  <button className={style.previos}>Назад</button>
  <button className={style.next}>Вперед</button>
</div>
     
   
      </>

         );
}
 
export default MainPageSetPromo; 
