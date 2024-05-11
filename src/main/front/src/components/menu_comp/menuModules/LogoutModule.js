import setupStyles from "../../../pages/stylesModules/setupStyles";

const LogoutModule = ()=> {
   const style = setupStyles("circlemenu")

    return (     
       <div  className={style.item_menu_a}> <a className={style.logbtn} >Выход</a> </div>
    );
  }
  export default LogoutModule;