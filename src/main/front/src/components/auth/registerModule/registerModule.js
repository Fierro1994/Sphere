import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { registerUser } from "../../redux/slices/authSlice";
import { useNavigate } from "react-router-dom";
import style from "../Login/login.module.css";
import { useForm } from "react-hook-form";
import Avatar from "react-avatar-edit";


const Register = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const [src, setSrc] = useState(null)
  const [preview, setPreview] = useState("")
 

  const onClose =()=>{
    setPreview("")
}
const onCrop = view =>{
    setPreview(view) 
}


  const {
    handleSubmit,
    formState: { errors, isValid },
    register,
    watch,

  } = useForm({
    mode: "onBlur"
  });

  useEffect(() => {
    // if (auth.registerStatus) {
    //   navigate("/");
    // }
  }, [auth._id, navigate, preview, setPreview]);

  const formData = new FormData();
  const onSubmit = (data) => {
    
    formData.append("email", data.email)
    formData.append("password", data.password)
    formData.append("firstName", data.firstName)
    formData.append("lastName", data.lastName)
    formData.append("roles", data.roles)
    formData.append("preview", preview)
      dispatch(registerUser(formData))
      
    }
   
   useEffect(() => {
    if(auth.registerStatus === "pending"){
      navigate("/")
    }
  }, [auth.registerStatus]);
  return (
<div>
    <form className={style.form_signin} onSubmit={handleSubmit(onSubmit)}>
      <div className={style.form_container}>
      <div className={style.avatar_img_cont}>
<Avatar
    label={"Выберите фото профиля"}
    labelStyle={{color:"white",
        fontSize:"1vw",
        cursor: "pointer",
        width: "100%",
        background: "url('/src/assets/169815923239.jpg')"}}
    width={400}
    height={300}
    
    onCrop={onCrop}
    
    onClose={onClose}
    
    src={src}
    
/>
<input className={style.hide}
        name ="preview"
        id="preview"
        value={preview}
        type="text"
        {...register("preview") }></input>
        
</div>
      <div>
        <input
          name="email"
          placeholder="email"
          type='text'
          {...register("email", {
            required: "Email обязателен!!!",
            pattern: {
              value: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,

              message: "Неправильный формат email",

            }
          })}
        ></input>
      </div>
      {errors && (<div className={style.errorcontainer}>
          {(errors?.email) && (
            <p className={style.error}>{errors.email.message}</p>
          )}</div>)}
      <div>
        <input
          name='password'
          id="password"
          placeholder="пароль"
          type='password'
          autoComplete='off'
          {...register("password", {
            // required: "Вы должны указать пароль",
            // pattern: {
            //   value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\w\s]).{6,}/,
            //   message: "Пароль должен содержать хотя бы одну цифру и один специальный символ.",
            // },
            // minLength: {
            //   value: 6,
            //   message: "Пароль должен быть более 6 символов"
            // },
            maxLength: {
              value: 20,
              message: "Пароль должен быть менее 20 символов"
            },
          })}
        ></input>
      </div>
      {errors && (<div className={style.errorcontainer}>
          {(errors?.password) && (
            <p className={style.error}>{errors.password.message}</p>
          )}</div>)}
      <div>
        <input
          id="confirmPassword"
          name="confirmPassword"
          placeholder="повторите пароль"
          type='password'
          {...register('confirmPassword', {
            validate: value =>
              value === watch("password", "") || "Пароли не совпадают"
          })}
          autoComplete='off'
          onPaste={(e) => {
            e.preventDefault();
            return false
          }}
        />
      </div>
      {errors && (<div className={style.errorcontainer} >
          {(errors?.confirmPassword) && (
            <p className={style.error}>{errors.confirmPassword.message}</p>
          )}</div>)}
      <div>

      <div>
        <input
          name="firstName"
          type='text'
          placeholder="имя"
          {...register("firstName", {
            minLength: {
              value: 3,
              message: "Имя должно быть более 3 символов"
            },
            maxLength: {
              value: 20,
              message: "Имя должно быть менее 20 символов"
            },
          })}
        ></input>
      </div>
      {errors && (<div className={style.errorcontainer}>
          {(errors?.firstName) && (
            <p className={style.error}>{errors.firstName.message}</p>
          )}</div>)}
        
        <div>
        <input
          name="lastName"
          type='text'
          placeholder="фамилия"
          {...register("lastName", {
            minLength: {
              value: 2,
              message: "Фамилия должна быть более 2 символов"
            },
            maxLength: {
              value: 20,
              message: "Фамилия должна быть менее 20 символов"
            },
          })}
        ></input>
      </div>
      {errors && (<div className={style.errorcontainer}>
          {(errors?.lastName) && (
            <p className={style.error}>{errors.lastName.message}</p>
          )}</div>)}

        <div>
          <button className={style.btn} >Регистрация</button>
        </div>
        <div>  {auth? <p className={style.error}></p> : <></>}</div>
        {auth.registerStatus === "pending" && <div style={{color : "white"}}>ожидайте регистрации..</div>}
      </div>
      </div>
    </form>
   
</div>
  );
};

export default Register;



