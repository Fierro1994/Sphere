import BlackTheme from "./BlackTheme/mainstyle.module.css";
import WhiteTheme from "./WhiteTheme/mainstyle.module.css";
import BlackThemeSetings from "./BlackTheme/settings.module.css";
import WhiteThemeSettings from "./WhiteTheme/settings.module.css";
import BlackMomentsRecord from "./BlackTheme/momentsrecord.module.css";
import WhiteMomentsRecord from "./WhiteTheme/momentsrecord.module.css";
import BlackMainPageSet from "./BlackTheme/mainpageset.module.css";
import BlackGaleryStyle from "./BlackTheme/galerypage.module.css";
import BlackMenuStyle from "./BlackTheme/circlemenu.module.css"
import WhiteMenuStyle from "./WhiteTheme/circlemenu.module.css"
import BlackThemeSliderPromo from "./BlackTheme/sliderpromo.module.css"

function setupStyles (name) {
 var style = ""
 const theme = localStorage.getItem("theme")
 if(!localStorage.getItem("theme")){
  style = BlackTheme
  return style
 }
  if(theme === "BLACK"){
    if (name === "mainstyle") {
      style = BlackTheme
      return style;
    }
    if (name === "circlemenu") {
      style = BlackMenuStyle
      return style;
    }

    if (name === "settingstyle") {
      style = BlackThemeSetings
      return style;
    }
    if (name === "sliderPromo") {
      style = BlackThemeSliderPromo
      return style;
    }
    if (name === "momentsrecordstyle") {
      style = BlackMomentsRecord
      return style;
    }
    if (name === "mainpagesetstyle") {
      style = BlackMainPageSet
      return style;
    }
    if (name === "galerypagestyle") {
      style = BlackGaleryStyle
      return style;
    }
    
  
  }
  if(theme === "WHITE"){
    if (name === "mainstyle") {
      style = WhiteTheme
    return style;
    }
    if (name === "circlemenu") {
      style = WhiteMenuStyle
      return style;
    }
    if (name === "settingstyle") {
      style = WhiteThemeSettings
      return style;
    }
    if (name === "momentsrecordstyle") {
      style = WhiteMomentsRecord
      return style;
    }
  }
  
 
 
}

export default setupStyles;