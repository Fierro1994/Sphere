import { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { registerUser } from "../../components/redux/slices/authSlice";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import getCroppedImg, { base64ToFile } from "../GaleryPage/GalleryAddPage/Crop";
import Cropper from "react-easy-crop";
import style from "../LoginPage/login.module.css";
import { MdOutlineAddAPhoto } from "react-icons/md";
const Register = () => {

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const [src, setSrc] = useState(null)
  const [preview, setPreview] = useState("")
  const [file, setFile] = useState(null);
  const [fileData, setFileData] = useState(null);
  const [showCropper, setShowCropper] = useState(true)
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [rotation, setRotation] = useState(0);
  const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);
  const [croppedImage, setCroppedImage] = useState(null);
  const [nameImg, setNameImage] = useState(null);

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

  const handleImageUpload = async (e) => {
    setFileData(e.target.files[0])
    setFile(URL.createObjectURL(e.target.files[0]));
    setNameImage((e.target.files[0].name));

 };

 const onCropComplete = useCallback((croppedArea, croppedAreaPixels) => {
  setCroppedAreaPixels(croppedAreaPixels);
}, []);

const showCroppedImage = useCallback(async () => {
  try {
     const croppedImage = await getCroppedImg(
        file,
        croppedAreaPixels,
        rotation
     );
     setCroppedImage(croppedImage);
     setShowCropper(false)
  } catch (e) {
     console.error(e);
  }
}, [croppedAreaPixels, rotation, file]);
  useEffect(() => {
    // if (auth.registerStatus) {
    //   navigate("/");
    // }
  }, [auth._id, navigate, preview, setPreview]);

  
  const onSubmit = (data) => {
    const file = base64ToFile(croppedImage, nameImg)
    const formData = new FormData();
    formData.append("email", data.email)
    formData.append("password", data.password)
    formData.append("firstName", data.firstName)
    formData.append("lastName", data.lastName)
    formData.append("roles", data.roles)
    formData.append("avatar", file)
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
      <div className={style.cont}>
      <div className={style.form_container}>
     
                  {!file&& <div className={style.avatar_change} >   
        <label className={style.cont_av_img}> <MdOutlineAddAPhoto className={style.avatar_svg}/>  <input
                           type="file"
                           name="cover"
                           onChange={handleImageUpload}
                           accept="img/jpeg, video/, img/jpg"
                           style={{ display: "none" }}
                          
                        />
                    </label></div>}
                    {file &&  <div className={style.avatar_img_cont}>
      {!croppedImage && <Cropper
                        image={file}
                        crop={crop}
                        rotation={rotation}
                        cropShape="round"
                        zoom={zoom}
                        zoomSpeed={0.5}
                        maxZoom={3}
                        zoomWithScroll={true}
                        showGrid={true}
                        aspect={4 / 4}
                        onCropChange={setCrop}
                        onCropComplete={onCropComplete}
                        onZoomChange={setZoom}
                        onRotationChange={setRotation}
                     />}
{croppedImage && (
                         <div className={style.avatar_img_cont_crop}>
                           <img className={style.promo_img} src={croppedImage} alt="cropped" />
                     </div>
                     )}

                     </div>
                     }


        {file && !croppedImage && <button className={style.cut_btn}
                     onClick={(e) => {
                        e.preventDefault()
                        showCroppedImage()
                     }}>
                    Готово
                  </button>}   

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
      </div>
    </form>
   
</div>
  );
};

export default Register;



