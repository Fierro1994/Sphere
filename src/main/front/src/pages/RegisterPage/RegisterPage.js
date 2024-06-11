import React from "react";
import Register from "./registerModule";
import setupStyles from "../stylesModules/setupStyles";

const RegisterPage = () => {
      const style = setupStyles("mainstyle")
    return (
        <>
        <div className={style.content}>
        <div className={style.mainContent}>
            <div className={style.main}>
                <h2>Регистрация пользователя</h2>

            </div>
           <Register/>
            </div>     
      </div>
      </>
         );
}
export default RegisterPage;


