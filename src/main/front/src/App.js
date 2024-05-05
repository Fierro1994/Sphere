import { BrowserRouter, Routes, Route, useNavigate, Link } from "react-router-dom";
import Home from "./pages/HomePage/Home";
import Register from "./pages/RegisterPage/RegisterPage";
import SettingsPageInterface from "./pages/SettingsPage/InterfacePage/SettingsPageInterface"
import { getLastTimeOnline, loadUser } from "./components/redux/slices/authSlice";
import { useEffect } from "react";
import { useDispatch, useSelector} from "react-redux";
import RequireAuth from "./components/auth/services/RequireAuth";
import ProfilePage from "./pages/ProfilePage/ProfilePage";
import authService from "./components/auth/services/authService";
import MomentsPage from "./pages/MomentsPage/PreviewPage/MomentsPage";
import MomentsAddPage from "./pages/MomentsPage/AddPage/MomentsAddPage";
import SettingsPageMainPage from "./pages/SettingsPage/settingsPageConstruktorMainPage/SettingsPageMainPage";
import Galery from "./pages/GaleryPage/Galery";
import SecurityPage from "./pages/SettingsPage/SecurityPage/SecurityPage";
import MomentsModuleView from "./pages/MomentsPage/module/MomentsModuleView";
import GaleryAdd from "./pages/GaleryPage/GalleryAddPage/GalleryAdd";
import MessagesPage from "./pages/MessagesPage/MessagesPage";
import KonstruktModuleProfile from "./pages/SettingsPage/settingsPageConstruktorMainPage/Modules/KonstruktModuleProfile";
import KonstruktProfilePage from "./pages/SettingsPage/settingsPageConstruktorMainPage/konstruktorProfilepage/KonstruktProfilePage";
import ArhivPage from "./pages/MomentsPage/arhivPage/ArhivPage";
import FriendsPage from "./pages/FriendsPage/FriendsPage";
import VideoGaleryPage from "./pages/GaleryPage/VideoGaleryPage/VideoGaleryPage";

function App() {
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);
  useEffect(() => {
    dispatch(loadUser(null));
    if (auth._id){
      authService.SetOnlineTime(auth._id)
      dispatch(getLastTimeOnline(auth._id))
    }
  }, [dispatch]);
  return (
    <div className="App">
      <BrowserRouter>
        <div className="content-container">
          <Routes>
           <Route path="/app/profile" element={<RequireAuth><ProfilePage /></RequireAuth>}/> 
           <Route path="/app/messages" element={<RequireAuth><MessagesPage /></RequireAuth>}/> 
           <Route path="/app/friends" element={<RequireAuth><FriendsPage /></RequireAuth>}/> 
           
           
           
             <Route path="/" element={<RequireAuth><Home /></RequireAuth>}/>
            <Route path="/app/moments/" element={<RequireAuth><MomentsPage /></RequireAuth>}/>
            <Route path="/app/moments/add/" element={<RequireAuth><MomentsAddPage /></RequireAuth>}/>
            <Route path= "/app/moments" element={<RequireAuth><MomentsModuleView /></RequireAuth>}/>
            <Route path= "/app/moments/arhiv" element={<RequireAuth><ArhivPage /></RequireAuth>}/>

           

            <Route path="/app/register" element={<Register />} />
            <Route path="/app/settings/interface" element={<RequireAuth><SettingsPageInterface /></RequireAuth>} />
            <Route path="/app/settings/mainpage" element={<RequireAuth><SettingsPageMainPage /></RequireAuth>} />
            <Route path="/app/settings/security" element={<RequireAuth><SecurityPage /></RequireAuth>} />
            <Route path="/app/settings/konstrukt/profile" element={<RequireAuth><KonstruktProfilePage /></RequireAuth>} />

           

           
            <Route path="/api/auth/confirm?token/:id" element={<RequireAuth><Home /></RequireAuth>} /> 
            <Route path="app/gallery/photo" element={<RequireAuth><Galery/></RequireAuth>} /> 
            <Route path="/app/gallery/add" element={<RequireAuth><GaleryAdd/></RequireAuth>} />
            <Route path="/app/gallery/video" element={<RequireAuth><VideoGaleryPage/></RequireAuth>} />

            
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;