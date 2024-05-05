import { useDispatch, useSelector } from "react-redux";
import { useForm } from "react-hook-form";
import setupStyles from "../../stylesModules/setupStyles";
import { setSelectedTheme } from "../../../components/redux/slices/authSlice";

export const CheckBoxInterface = () => {
   const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const style = setupStyles("settingstyle")
  const { register, handleSubmit } = useForm({
    mode: "all",
  });

  const onSubmit = (data) => {
    const formData = new FormData();
      formData.append("name", data.radio)
      formData.append("userId", auth._id)
    dispatch(setSelectedTheme(formData))
  }

  return (
    <>
        <form  onSubmit={handleSubmit(onSubmit)}>
       
            <div className={style.checkbox_module}>
            <p className={style.labelmenu}>Тема:</p>
              <label> 
                <input  {...register("radio")} defaultChecked={auth.theme === "BLACK" ? true : false} className={style.check} type="radio" value="BLACK" onInput={handleSubmit(onSubmit)} /> 
                <span type="submit" className={style.customcheck}> </span>
                 Темная 
                </label>
                <label> 
                <input  {...register("radio")} defaultChecked={auth.theme === "WHITE" ? true : false} className={style.check} type="radio" value="WHITE" onInput={handleSubmit(onSubmit)} /> 
                <span type="submit" className={style.customcheck}> </span>
                 Светлая 
                </label>
            </div>
        </form>
    </>
  )
}