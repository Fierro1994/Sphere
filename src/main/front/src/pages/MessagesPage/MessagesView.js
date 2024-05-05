import setupStyles from "../stylesModules/setupStyles";

const MessagesView =() => {

  const style = setupStyles("mainstyle")
   
   return (

<div className={style.tablemain}>
<form className={style.form_profile}>
<table className={style.tablemain}>
  <thead>
      <tr>
        <th>Сообщения</th>
      </tr>
  </thead>
</table>

</form>
 </div>
   )
}

export default MessagesView;


  