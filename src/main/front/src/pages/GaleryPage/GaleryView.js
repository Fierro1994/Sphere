import setupStyles from "../stylesModules/setupStyles";
import GaleryFileManager from "./service/GaleryFileManager";

const GaleryView =() => {

  const style = setupStyles("mainstyle")
   
   return (

<div className={style.tablemain}>
<form className={style.form_profile}>
<table className={style.tablemain}>
  <thead>
      <tr>
        <th>Галерея</th>
      </tr>
      <tr>
      <GaleryFileManager/>
      </tr>
  </thead>
</table>

</form>
 </div>
   )
}

export default GaleryView;


  