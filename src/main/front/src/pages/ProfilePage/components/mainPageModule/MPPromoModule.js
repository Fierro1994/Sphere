import { useEffect } from "react";
import setupStyles from "../../../stylesModules/setupStyles";
import Slider from '../service/Slider';


const MPPromoModule = () => {
   const style = setupStyles("mainpagesetstyle")
   return (
      <>
         <div className={style.slider_container}>
            <Slider/>
         </div>
      </>

   );
}

export default MPPromoModule; 
