import setupStyles from "../stylesModules/setupStyles";

const TreeView =() => {

  const style = setupStyles("mainstyle")
   
   return (

<div className={style.tablemain}>
<form className={style.form_profile}>
<table className={style.tablemain}>
  <thead>
      <tr>
        <th>Семья</th>
      </tr>
  </thead>
</table>

</form>
 </div>
   )
}

export default TreeView;


  