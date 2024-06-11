import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../../stylesModules/setupStyles";
import {addFriendSubscribe, getFriendSubscribe, searchFriends, updateFriendsList } from "../../../components/redux/slices/friendsSlice";
import { useEffect, useState } from "react";

const SubscribeView =() => {
  const PATH = "http://localhost:3000/avatar"
  const style = setupStyles("friendsPage")
  const auth = useSelector((state) => state.auth);
  const friendsSl = useSelector((state) => state.friendsred);
  const[showPop, setShowPop] = useState(false)
const dispatch = useDispatch()

function resultsrc(el) {
  return PATH + "/" + el.userId + "/" + el.avatar
}

function dateFormat(time) {
  var time_day = ""
  var hours = time[3]
  var minutes = time[4]
  var day = time[2]
  var mounth = time[1]

  var now = new Date()

  if(day < 10) day = "0" + day
  if(mounth < 10) mounth = "0" + mounth

  if (day !== now.getDay()) {
    time_day = day + "-" + mounth + " в "
  }
  var dateFormat = "Был(а): " + time_day + " " + hours + ":" + minutes
  return dateFormat
}

useEffect(() => {
  dispatch(getFriendSubscribe(auth._id))
}, []);


function addFriendHandler(user) {
  const myData = new FormData();
  myData.append('user', auth._id);
  myData.append('targetUser', user);
  dispatch(addFriendSubscribe(myData))
}
  
  const result =
    friendsSl.subscribersList?.map((el, i) => {
      return( 
        <div key={i} className={style.search_avatar} style={{
          left: i * 10 + "vw",
        }}>
      <div>
      <img onClick={()=> setShowPop(!showPop)} className={style.avatar_img} src={resultsrc(el)}  />
      <div className={style.name_p}><p className={style.name}>{el.firstName + " " + el.lastName}</p></div>
      <div className={style.online_p}><p className={style.name}>{dateFormat(el.lastTimeOnline)}</p></div>
      </div>
      <div className={!showPop ? style.popup_cont : style.popup_cont +  " " + style.open}>
       <div>
       <span className={style.btn_pop}>Открыть профиль</span>
        <span className={style.btn_pop} onClick={()=> {
          setShowPop(false)
          addFriendHandler(el.userId)}}>Добавить в друзья</span>
       </div>
      
        </div>
      </div>
      )

    })
   
  return (
<div className={style.tablemain}>
<h2>Заявки в друзья</h2>

<div className={style.display_info}><p>Всего найдено: {friendsSl.subscribersList?.length}</p> </div>
  
  {result}
    
</div>
   )
}

export default SubscribeView;


  