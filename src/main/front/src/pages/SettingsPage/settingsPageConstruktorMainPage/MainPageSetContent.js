import setupStyles from "../../stylesModules/setupStyles";
import MainPageSetCarts from "./MainPageSetCarts";


const MainPageSetContent = () => {
   const style = setupStyles("mainpagesetstyle")
    return (
      <>
      <div className={style.content}>
      <div className={style.containercontent}>
      <div><MainPageSetCarts/></div>
      <div className={style.content_maincontent}>
      <p>Основной контент</p>
      <button className={style.plus}>+</button>
      </div>
      
      </div>
      </div>
      </>

         );
}
 
export default MainPageSetContent; 
