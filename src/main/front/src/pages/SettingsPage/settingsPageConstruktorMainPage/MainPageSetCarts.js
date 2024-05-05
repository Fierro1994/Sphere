import setupStyles from "../../stylesModules/setupStyles";


const MainPageSetCarts = () => {
   const style = setupStyles("mainpagesetstyle")
    return (
      <>
      <div className={style.carts}>
      <p>Карточки</p>
      <button className={style.plus}>+</button>
      
      </div>
      </>

         );
}
 
export default MainPageSetCarts; 
