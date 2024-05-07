import setupStyles from "../stylesModules/setupStyles";
import mainPageModuleChanger from "../../components/mainpagemodules/mainPageModuleChanger";

const Content =() => {
  const style = setupStyles("mainstyle")
  const listMainPageModules = JSON.parse(localStorage.getItem("mainPageModules"))
  const result = mainPageModuleChanger(listMainPageModules)

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


  