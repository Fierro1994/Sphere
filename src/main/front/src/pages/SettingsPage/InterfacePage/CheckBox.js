import { useDispatch, useSelector } from "react-redux";
import { useForm } from "react-hook-form";
import { useState } from "react";
import setupStyles from "../../stylesModules/setupStyles";
import { updateSelectedMenu } from "../../../components/redux/slices/mainMenuSlice";

export const Checkbox = () => {
   const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const mainmenuslice = useSelector((state) => state.mainmenu);
  const items = JSON.parse(localStorage.getItem("menuModules"));
  const formData = new FormData();
  const style = setupStyles("settingstyle")
  const [disabledButton, setDisabledButton] = useState(true)
  const { register, handleSubmit } = useForm({
    mode: "all",
    defaultValues: {
      checkbox: [],
    }
  });

  const onSubmit = (data) => {
   
    if (items.length-3 === data.checkbox.length || items.length<4)
    {setDisabledButton(true)}
    formData.append("userId", auth._id)
    data.checkbox.map(el => {
      formData.append("name", el)
      formData.append("userId", auth._id)
    })
    dispatch(updateSelectedMenu(formData))
  
  }

  return (
    <>
        <form className={style.form_check}  onSubmit={handleSubmit(onSubmit)}>
          {auth._id && items.map((item) =>
          (
            
            <div className={style.checkbox_module} key={item.id}>
              <label> 
                <input  {...register("checkbox")} className={style.check} type="checkbox" value={item.name} onInput={handleSubmit(onSubmit)} defaultChecked={item.isEnabled} onClick={() => {
              
                  setDisabledButton(false)}}/> 
                <span type="submit" className={style.customcheck}> </span>
                {item.nametwo}
                </label>
                
             
            </div>
          ))}
        </form>
    </>
  )
}