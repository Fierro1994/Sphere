import setupStyles from "../../../stylesModules/setupStyles";
import Slider from '../../../ProfilePage/components/service/Slider';


const MainPageSetPromo = () => {
   const style = setupStyles("mainpagesetstyle")
  
   return (
      <>
         <Slider />
         <div className={style.slider_container}>
            <button className={style.previos}>Назад</button>
            <button className={style.next}>Вперед</button>
         </div>
      </>

   );
}

export default MainPageSetPromo; 
