import setupStyles from "../../stylesModules/setupStyles";


const MainPageSetInfo = () => {
   const style = setupStyles("mainpagesetstyle")
    return (
      <>
      <div className={style.info}>
      <p>Контакты</p>
      <p>имя</p>
      <p>телефон</p>
      </div>
      </>

         );
}
 
export default MainPageSetInfo; 
