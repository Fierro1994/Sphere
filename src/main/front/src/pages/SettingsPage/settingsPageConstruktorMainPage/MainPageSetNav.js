import setupStyles from "../../stylesModules/setupStyles";


const MainPageSetNav = () => {
   const style = setupStyles("mainpagesetstyle")
    return (
      <>
      <div className={style.nav}>
      <p>Навигация</p>
      <button className={style.plus}>+</button>
      
      </div>
      </>

         );
}
 
export default MainPageSetNav; 
