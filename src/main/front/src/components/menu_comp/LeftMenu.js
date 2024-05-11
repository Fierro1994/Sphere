import React from "react";
import setupStyles from "../../pages/stylesModules/setupStyles";
import menuModuleChange from "./menuModuleChange";

function LeftMenu(nameModule, namePage) {
  const style = setupStyles("circlemenu")
  let listModuleName = menuModuleChange(nameModule)

  const result = listModuleName.map((element, i) => {
    if (element.type.name === namePage) {
      return <span className={style.small_circle_this} key={i} style={{
        top: i * 6 + "vw",
      }}>{element}</span>
    }
    return <span className={style.small_circle} key={i} style={{
      top: i * 6 + "vw",
    }}>{element}</span>
  });
  return (
    <>
      {result}
    </>
  )
}

export default LeftMenu;



