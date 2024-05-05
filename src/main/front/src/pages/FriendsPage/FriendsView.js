import setupStyles from "../stylesModules/setupStyles";

const FriendsView =() => {

  const style = setupStyles("mainstyle")
   
   return (

<div className={style.tablemain}>
<form className={style.form_profile}>
<table className={style.tablemain}>
  <thead>
      <tr>
        <th>Друзья</th>
      </tr>
  </thead>
</table>

</form>
 </div>
   )
}

export default FriendsView;


  