import setupStyles from "../stylesModules/setupStyles";
import mainPageModuleChanger from "./components/mainPageModuleChanger";

const Content =() => {
  let result = [] 
  const style = setupStyles("mainstyle")
  if (localStorage.getItem("mainPageModules")) {
    const listMainPageModules = JSON.parse(localStorage.getItem("mainPageModules"))
    result = mainPageModuleChanger(listMainPageModules)
  }



return (
<div className={style.tablemain}>
  {result.map((element, i) =>
  {
    return (<span key={i}>{element}</span>)
  }
  )}

 </div>
   )
}

export default Content;


  