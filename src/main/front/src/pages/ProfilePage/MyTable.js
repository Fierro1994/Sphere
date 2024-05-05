import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../stylesModules/setupStyles";

const MyTable =() => {

  const style = setupStyles("mainstyle")
   
   return (

<div>
<form className={style.form_profile}>
<table className={style.tablemain}>
  <thead>
      <tr>
        <th>Мой профиль</th>
      </tr>
  </thead>
  <tbody>
        <tr >
          <td>Статус</td>
        </tr>
        <tr >
          <td>Статус</td>
        </tr>
        <tr >
          <td>Статус</td>
        </tr>
        <tr >
          <td>Статус</td>
        </tr>
  </tbody>
</table>

</form>
 </div>
   )
}

export default MyTable;


  