import { BrowserRouter, Routes, Route, useNavigate, Link } from "react-router-dom";
import ProfilePage from "./pages/ProfilePage/ProfilePage";
import Register from "./pages/RegisterPage/RegisterPage";
import SettingsPageInterface from "./pages/SettingsPage/InterfacePage/SettingsPageInterface"
import HomePage from "./pages/HomePage/HomePage";
import MomentsPage from "./pages/MomentsPage/PreviewPage/MomentsPage";
import MomentsAddPage from "./pages/MomentsPage/AddPage/MomentsAddPage";
import SettingsPageMainPage from "./pages/SettingsPage/settingsPageConstruktorMainPage/konstrMainPage/SettingsPageMainPage";
import Galery from "./pages/GaleryPage/Galery";
import SecurityPage from "./pages/SettingsPage/SecurityPage/SecurityPage";
import MomentsModuleView from "./pages/MomentsPage/module/MomentsModuleView";
import GaleryAdd from "./pages/GaleryPage/GalleryAddPage/GalleryAdd";
import MessagesPage from "./pages/MessagesPage/MessagesPage";
import KonstruktProfilePage from "./pages/SettingsPage/settingsPageConstruktorMainPage/konstruktorProfilepage/KonstruktProfilePage";
import ArhivPage from "./pages/MomentsPage/arhivPage/ArhivPage";
import FriendsPage from "./pages/FriendsPage/FriendsPage";
import VideoGaleryPage from "./pages/GaleryPage/VideoGaleryPage/VideoGaleryPage";
import TreePage from "./pages/TreePage/TreePage";
import SearchFrPage from "./pages/FriendsPage/searchPage/SearchFrPage";
import SubscribPage from "./pages/FriendsPage/subscribePage/SubscribPage";
import SubscriptionsPage from "./pages/FriendsPage/subscriptionsPage/SubscriptionsPage";
import UsersPage from "./pages/UsersPage/UsersPage";
import { RequireAuth } from "./components/auth/api/RequireAuth";
import Login from "./pages/LoginPage/Login"
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <div className="content-container">
          <Routes>
           <Route path="/app/profile" element={<RequireAuth><ProfilePage /></RequireAuth>}/>
           <Route path="/app/messages" element={<RequireAuth><MessagesPage /></RequireAuth>}/> 

           <Route path="/app/friends" element={<RequireAuth><FriendsPage /></RequireAuth>}/> 
            <Route path="/app/friends/search/allusers" element={<RequireAuth><SearchFrPage /></RequireAuth>}/> 
            <Route path="/app/friends/subscribe" element={<RequireAuth><SubscribPage /></RequireAuth>}/> 
            <Route path="/app/friends/subscriptions" element={<RequireAuth><SubscriptionsPage /></RequireAuth>}/> 

            <Route path="/app/friends/:id" element={<RequireAuth><UsersPage /></RequireAuth>} /> 
           
             <Route path="/" element={<RequireAuth><HomePage /></RequireAuth>}/>
             <Route path="/login" element={<Login/>} />

            <Route path="/app/moments/" element={<RequireAuth><MomentsPage /></RequireAuth>}/>
            <Route path="/app/moments/add/" element={<RequireAuth><MomentsAddPage /></RequireAuth>}/>
            <Route path= "/app/moments" element={<RequireAuth><MomentsModuleView /></RequireAuth>}/>
            <Route path= "/app/moments/arhiv" element={<RequireAuth><ArhivPage /></RequireAuth>}/>

           

            <Route path="/app/register" element={<Register />} />
            <Route path="/app/settings/interface" element={<RequireAuth><SettingsPageInterface /></RequireAuth>} />
            <Route path="/app/settings/mainpage" element={<RequireAuth><SettingsPageMainPage /></RequireAuth>} />
            <Route path="/app/settings/security" element={<RequireAuth><SecurityPage /></RequireAuth>} />
            <Route path="/app/settings/konstrukt/profile" element={<RequireAuth><KonstruktProfilePage /></RequireAuth>} />

           
            <Route path="/app/gallery/photo" element={<RequireAuth><Galery/></RequireAuth>} /> 
            <Route path="/app/gallery/add" element={<RequireAuth><GaleryAdd/></RequireAuth>} />
            <Route path="/app/gallery/video" element={<RequireAuth><VideoGaleryPage/></RequireAuth>} />

            <Route path="/app/familytree" element={<RequireAuth><TreePage/></RequireAuth>} />

            
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}
export default App;