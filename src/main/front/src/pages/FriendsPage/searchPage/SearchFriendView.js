import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../../stylesModules/setupStyles";
import {addFriendSubscribe, searchFriends, updateFriendsList } from "../../../components/redux/slices/friendsSlice";
import { useEffect, useState } from "react";

const SearchFriendView =() => {

  const style = setupStyles("friendsPage")
  const auth = useSelector((state) => state.auth);
  const friendsSl = useSelector((state) => state.friendsred);
  const[showPop, setShowPop] = useState(false)
const dispatch = useDispatch()
const PATH = "http://localhost:3000/avatar"

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
  var dateFormat = "Был(а): " + time_day + " " + time[3] + ":" + time[4]
  return dateFormat
}

useEffect(() => {
  dispatch(searchFriends(auth._id))
}, []);

function resultsrc(el) {
  return PATH + "/" + el.userId + "/" + el.avatar
}

function addFriendHandler(user) {
  const myData = new FormData();
  myData.append('user', auth._id);
  myData.append('targetUser', user);
  dispatch(addFriendSubscribe(myData))
}
  
  const result =
    friendsSl.searchFrList?.map((el, i) => {
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
<h2>Поиск друзей</h2>

<form>
   <p>
    <input type="search" name="q" className={style.search_input} placeholder="Введите имя или id друга"/> 
   <input type="submit" className={style.search_submit} value="Найти"/></p>
  </form>
<div className={style.display_info}><p>Всего найдено: {friendsSl.searchFrList?.length}</p> </div>
  
  {result}
    
</div>
   )
}

export default SearchFriendView;


  