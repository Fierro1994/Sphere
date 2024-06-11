import setupStyles from "../../../pages/stylesModules/setupStyles";


const UPNavModule = () => {
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
 
export default UPNavModule; 
