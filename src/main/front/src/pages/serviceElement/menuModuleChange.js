import ProfileAddModules from './menuModules/SettingsModules.js';
import LogoutModule from "./menuModules/LogoutModule.js";
import SettingsPageModuleInterface from "../SettingsPage/modules/SettingsPageModuleInterface.js"
import SettingsPageModuleSecurity from "../SettingsPage/modules/SettingsPageModuleSecurity.js"
import MainModule from "./menuModules/MainModule.js";
import MomentsModuleArhiv from "../MomentsPage/module/MomentsModuleArhiv.js";
import MomentsModuleAdd from "../MomentsPage/module/MomentsModuleAdd.js";
import MomentsModuleView from "../MomentsPage/module/MomentsModuleView.js";
import MomentsModuleVideo from "../MomentsPage/AddPage/modules/MomentsModuleVideo.js";
import MomentsModulePhoto from "../MomentsPage/AddPage/modules/MomentsModulePhoto.js";
import MomentsModuleBack from "../MomentsPage/module/MomentsModuleBack.js";
import GalleryModuleAdd from "../GaleryPage/module/GalleryModuleAdd.js"
import GalleryModulePhoto from "../GaleryPage/module/galleryModulePhoto/GalleryModulePhoto.js"
import GalleryModuleVideo from "../GaleryPage/module/GalleryModuleVideo.js"
import GalleryModuleArhiv from "../GaleryPage/module/GalleryModuleArhiv.js"
import GalleryAddModuleBack from "../GaleryPage/GalleryAddPage/module/GalleryAddModuleBack.js";
import MomentsModule from "./menuModules/MomentsModule.js";
import GaleryModule from "./menuModules/GaleryModule.js";
import ProfileModule from "./menuModules/ProfileModule.js";
import MessagesModule from "./menuModules/MessagesModule.js";
import FriendsModule from "./menuModules/FriendsModule.js";
import SettingsPageModuleKonstruktor from '../SettingsPage/modules/SettingsPageModuleKonstruktor.js';
import KonstruktModuleProfile from '../SettingsPage/settingsPageConstruktorMainPage/Modules/KonstruktModuleProfile.js';

function menuModuleChange(nameModule) {
  let listMenuModules = []
  const listModuleName = []
  if (nameModule === "menuModules") {
    if (localStorage.getItem(nameModule)) {
      listMenuModules = JSON.parse(localStorage.getItem(nameModule))
    }
    listModuleName.push(<MainModule />)
    listMenuModules.forEach(element => {
      if (element.isEnabled && element.name === "MOMENTS") {
        listModuleName.push(<MomentsModule />)
      }
      if (element.isEnabled && element.name === "GALERY") {
        listModuleName.push(<GaleryModule />)
      }
      if (element.isEnabled && element.name === "PROFILE_INFO") {
        listModuleName.push(<ProfileModule />)
      }
      if (element.isEnabled && element.name === "MESSAGES") {
        listModuleName.push(<MessagesModule />)
      }
      if (element.isEnabled && element.name === "FRIENDS") {
        listModuleName.push(<FriendsModule />)
      }
    });
    listModuleName.push(<ProfileAddModules />)
    listModuleName.push(<LogoutModule />)
  }

  if (nameModule === "settingsInterface") {
    listModuleName.push(<SettingsPageModuleInterface />)
    listModuleName.push(<SettingsPageModuleSecurity />)
    listModuleName.push(<SettingsPageModuleKonstruktor />)
  }

  if (nameModule === "settingsConstruktor") {
    listModuleName.push(<SettingsPageModuleInterface />)
    listModuleName.push(<SettingsPageModuleSecurity />)
    listModuleName.push(<SettingsPageModuleKonstruktor />)
    listModuleName.push(<KonstruktModuleProfile />)
  }


  if (nameModule === "momentsPage") {
    listModuleName.push(<MomentsModuleView />)
    listModuleName.push(<MomentsModuleArhiv />)
    listModuleName.push(<MomentsModuleAdd />)

  }

  if (nameModule === "momentsAdd") {
    listModuleName.push(<MomentsModuleBack />)
    listModuleName.push(<MomentsModulePhoto />)
    listModuleName.push(<MomentsModuleVideo />)

  }
  if (nameModule === "galleryModule") {
    listModuleName.push(<GalleryModuleAdd />)
    listModuleName.push(<GalleryModulePhoto />)
    listModuleName.push(<GalleryModuleVideo />)
    listModuleName.push(<GalleryModuleArhiv/>)

    
  }

  if (nameModule === "galleryModuleAdd") {
    listModuleName.push(<GalleryAddModuleBack />)
    listModuleName.push(<GalleryModulePhoto />)
    listModuleName.push(<GalleryModuleVideo />)
    listModuleName.push(<GalleryModuleAdd />)
  }

  


  return listModuleName

}

export default menuModuleChange;