import setupStyles from "../../../pages/stylesModules/setupStyles";


const MPNavModule = () => {
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
 
export default MPNavModule; 
