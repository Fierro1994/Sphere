import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { loginUser } from "../../components/redux/slices/authSlice";
import { Link, useNavigate } from "react-router-dom";
import stylecheck from "../../components/style_modules/checkbox.module.css";
import { useForm } from "react-hook-form";
import setupStyles from "../stylesModules/setupStyles";


const Login = () => {
  const style = setupStyles("logReg")
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  const [showMessage, setShowMessage] = useState(false);

  const [diffS, setDiff] = useState(5);
  const [tick, setTick] = useState(false);

  useEffect(() => {
    if (auth.loginStatus === "rejected") {
      viewMessage();
      setShowMessage(true);
    } else {
      setShowMessage(false);
    }
  }, [auth.loginStatus]);

  useEffect(() => {
    if (showMessage) {
      const timeout = setTimeout(() => {
        setShowMessage(false);
      }, 3000); // 3 секунды
      return () => clearTimeout(timeout);
    }
  }, [showMessage]);

  const viewMessage = () => {
    setShowMessage(true);
  };

  useEffect(() => {
    if (diffS == 0) return
    setDiff(
      diffS - 1
    )
  }, [tick])

  useEffect(() => {
    const timerID = setInterval(() => setTick(!tick), 1000);
    return () => clearInterval(timerID);
  }, [tick])

  const [registerSucess, setRegisterSucess] = useState(false);

  const {
    handleSubmit,
    formState: { errors, isValid },
    register,
    watch,
  } = useForm({
    mode: "onBlur"
  });

  const onSubmit = (data) => {
    try {
      dispatch(loginUser(data))
    } catch (error) {
    }
  }

  useEffect(() => {
    if (auth.loginStatus === "success") {
      window.location.href = "/"
    }
  }, [auth.loginStatus]);


  useEffect(() => {
    if (auth.loginError) {
      console.log(auth.loginError);
    }
  }, [auth.loginError]);

  useEffect(() => {
    if (auth.registerStatus === "pending") {
      setRegisterSucess(true)
      setTimeout(setRegisterSucessOff, 4000);
    }else {
      setRegisterSucess(false)
    }
  }, [auth.registerStatus]);

  function setRegisterSucessOff() {
    setRegisterSucess(false);
  }

  return (
    <div className={style.container}>
      <div className={style.content}>
        <div className={style.main}>
          <h2>Добро пожаловать</h2>
          <div className={!registerSucess ? style.registerSucessModal : style.registerSucessModal + " " + style.registerSucessModalOn}>
            <div className={style.window_popup}>
              <div className={style.container_popup}>
                <p>Cпасибо за регистрацию!</p>
                <p>Подтвердите учетную запись, по ссылке из сообщения, отправленном на указанный вами e-mail</p>
                <div className={style.loader_container}><p className={style.loader}></p>
                  <div className={style.timer_container}>{`${diffS.toString().padStart(2, '0')}`}</div>
                </div>
              </div>
            </div>
          </div>
          <form className={style.form_signin} onSubmit={handleSubmit(onSubmit)}>
            <div className={style.form_container}>
              <div>
                <input
                  name="email"
                  type='text'
                  placeholder="email"
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
                  type='password'
                  placeholder="пароль"
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

              <div className={stylecheck.checkbox_container}>
                <label> Запомнить меня</label>
                <input {...register("checkbox")} className={stylecheck.check} type="checkbox" />
              </div>
              {errors && (<div className={style.errorcontainer} >
                {(errors?.rememberme) && (
                  <p className={style.error}>{errors.rememberme.message}</p>
                )}</div>)}
              <div>
                <div>
                  <button className={style.btn} >Войти</button>
                 {showMessage && <p style={{color: "#fff"}}>{auth.loginError}</p>}
                </div>
                <div>  {auth ? <p className={style.error}>{ }</p> : <></>}</div>
              </div>
              <Link className={style.btn_link} to={"/app/register/"}>Регистрация</Link>
            </div>

          </form>

        </div></div>
    </div>
  );
};

export default Login;



