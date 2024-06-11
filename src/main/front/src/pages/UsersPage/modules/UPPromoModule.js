import setupStyles from "../../../pages/stylesModules/setupStyles";
import UPSlider from "../service/UPSlider";


const UPPromoModule = () => {
   const style = setupStyles("mainpagesetstyle")
  
   return (
      <>
        
         <div className={style.slider_container}>
         <UPSlider />
         </div>
      </>

   );
}

export default UPPromoModule; 
