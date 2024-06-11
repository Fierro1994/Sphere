import setupStyles from "../../../pages/stylesModules/setupStyles";


const UPContentModule = () => {
   const style = setupStyles("mainpagesetstyle")
    return (
      <>
      <div className={style.content}>
      <div className={style.containercontent}>
      <div></div>
      <div className={style.content_maincontent}>
      <p>Основной контент</p>
      <button className={style.plus}>+</button>
      </div>
      
      </div>
      </div>
      </>

         );
}
 
export default UPContentModule; 
