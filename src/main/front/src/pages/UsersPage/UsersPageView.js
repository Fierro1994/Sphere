import setupStyles from "../stylesModules/setupStyles";
import { getUsersData } from "../../components/redux/slices/usersPageSlice";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import UsersPageModuleChanger from "./service/UsersPageModuleChanger";

const UsersPageView =({id}) => {
  const style = setupStyles("mainstyle")
  const usersPage = useSelector((state) => state.userspages);
  const listMainPageModules = usersPage.mainPageModules;
  const result = UsersPageModuleChanger(listMainPageModules)
  const dispatch = useDispatch();
  
  useEffect(() => {
    dispatch(getUsersData(id));
  }, []);
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

export default UsersPageView;
