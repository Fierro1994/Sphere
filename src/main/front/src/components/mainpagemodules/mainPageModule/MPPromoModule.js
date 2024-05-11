import setupStyles from "../../../pages/stylesModules/setupStyles";
import Slider from '../service/Slider';


const MPPromoModule = () => {
   const style = setupStyles("mainpagesetstyle")
  
   return (
      <>
        
         <div className={style.slider_container}>
         <Slider />
         </div>
      </>

   );
}

export default MPPromoModule; 
