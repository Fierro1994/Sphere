import ProfileAddModules from './menuModules/SettingsModules.js';
import LogoutModule from "./menuModules/LogoutModule.js";
import SettingsPageModuleInterface from "../../pages/SettingsPage/modules/SettingsPageModuleInterface.js"
import SettingsPageModuleSecurity from "../../pages/SettingsPage/modules/SettingsPageModuleSecurity.js"
import MainModule from "./menuModules/MainModule.js";
import MomentsModuleArhiv from "../../pages/MomentsPage/module/MomentsModuleArhiv.js";
import MomentsModuleAdd from "../../pages/MomentsPage/module/MomentsModuleAdd.js";
import MomentsModuleView from "../../pages/MomentsPage/module/MomentsModuleView.js";
import MomentsModuleVideo from "../../pages/MomentsPage/AddPage/modules/MomentsModuleVideo.js";
import MomentsModulePhoto from "../../pages/MomentsPage/AddPage/modules/MomentsModulePhoto.js";
import MomentsModuleBack from "../../pages/MomentsPage/module/MomentsModuleBack.js";
import GalleryModuleAdd from "../../pages/GaleryPage/module/GalleryModuleAdd.js"
import GalleryModulePhoto from "../../pages/GaleryPage/module/galleryModulePhoto/GalleryModulePhoto.js"
import GalleryModuleVideo from "../../pages/GaleryPage/module/GalleryModuleVideo.js"
import GalleryModuleArhiv from "../../pages/GaleryPage/module/GalleryModuleArhiv.js"
import GalleryAddModuleBack from "../../pages/GaleryPage/GalleryAddPage/module/GalleryAddModuleBack.js";
import MomentsModule from "./menuModules/MomentsModule.js";
import GaleryModule from "./menuModules/GaleryModule.js";
import ProfileModule from "./menuModules/ProfileModule.js";
import MessagesModule from "./menuModules/MessagesModule.js";
import FriendsModule from "./menuModules/FriendsModule.js";
import SettingsPageModuleKonstruktor from '../../pages/SettingsPage/modules/SettingsPageModuleKonstruktor.js';
import KonstruktModuleProfile from '../../pages/SettingsPage/settingsPageConstruktorMainPage/Modules/KonstruktModuleProfile.js';
import TreeModules from './menuModules/TreeModules.js';
import FriendsModuleLeft from '../../pages/FriendsPage/modules/FriendsModuleLeft.js';
import SubscribeUsersModule from '../../pages/FriendsPage/modules/SubscribeUsersModule.js';
import BlockUserModule from '../../pages/FriendsPage/modules/BlockUserModule.js';
import SearchFriendModule from '../../pages/FriendsPage/modules/SearchFriendModule.js';

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
      if (element.isEnabled && element.name === "TREE") {
        listModuleName.push(<TreeModules />)
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
  if (nameModule === "friendsModule") {
    listModuleName.push(<FriendsModuleLeft />)
    listModuleName.push(<SearchFriendModule />)
    listModuleName.push(<SubscribeUsersModule />)
    listModuleName.push(<BlockUserModule />)
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