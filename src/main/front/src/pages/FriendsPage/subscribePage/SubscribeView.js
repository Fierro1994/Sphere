import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../../stylesModules/setupStyles";
import {addFriendSubscribe, getFriendSubscribe, searchFriends, updateFriendsList } from "../../../components/redux/slices/friendsSlice";
import { useEffect, useState } from "react";
import {instanceWidthCred} from "../../../components/auth/api/instance";
import defsrc from "../../../assets/defavatar.jpg";

const SubscribeView =() => {
  const style = setupStyles("friendsPage")
  const auth = useSelector((state) => state.auth);
  const friendsSl = useSelector((state) => state.friendsred);
  const[showPop, setShowPop] = useState(false)
const dispatch = useDispatch()
    const [imageUrls, setImageUrls] = useState({});


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
  dispatch(getFriendSubscribe())
}, [dispatch]);

    const fetchImage = async (imageUrl, userId) => {
        try {
            const response = await instanceWidthCred.get(imageUrl, { responseType: 'blob' });
            const imageUrlBlob = URL.createObjectURL(response.data);
            setImageUrls((prev) => ({ ...prev, [userId]: imageUrlBlob }));
        } catch (error) {
            console.error('Error fetching image:', error);
        }
    };

    useEffect(() => {
        if (friendsSl.subscribersList) {
            friendsSl.subscribersList.forEach((el) => {
                if (el.avatars[0]) {
                    fetchImage(el.avatars[0], el.userId);
                }
            });
        }
    }, [friendsSl.subscribersList]);

function addFriendHandler(user) {
  const myData = new FormData();
  myData.append('user', auth._id);
  myData.append('targetUser', user);
  dispatch(addFriendSubscribe(myData))
}

    const result = friendsSl.subscribersList?.map((el, i) => {
        return (
            <div key={i} className={style.search_avatar}>
                <div>
                    <img
                        onClick={() => setShowPop(showPop === el.userId ? null : el.userId)} // Toggle menu for specific user
                        className={style.avatar_img}
                        src={imageUrls[el.userId] || defsrc}
                        alt={`${el.firstName} ${el.lastName}`}
                    />
                    <div className={style.name_p}>
                        <p className={style.name}>{el.firstName + " " + el.lastName}</p>
                    </div>
                    <div className={style.online_p}>
                        <p className={style.name}>{dateFormat(el.lastTimeOnline)}</p>
                    </div>
                </div>
                <div className={showPop === el.userId ? style.popup_cont + " " + style.open : style.popup_cont}>
                    <div>
                        <span className={style.btn_pop}>Открыть профиль</span>
                        <span
                            className={style.btn_pop}
                            onClick={() => {
                                setShowPop(false);
                                addFriendHandler(el.userId);
                            }}
                        >
              Добавить в друзья
            </span>
                    </div>
                </div>
            </div>);

    });

    return (
        <div className={style.tablemain}>
            <h2>Поиск друзей</h2>
            <form>
                <p>
                    <input type="search" name="q" className={style.search_input} placeholder="Введите имя или id друга" />
                    <input type="submit" className={style.search_submit} value="Найти" />
                </p>
            </form>
            <div className={style.display_info}>
                <p>Всего найдено: {friendsSl.subscribersList?.length}</p>
            </div>
            <div className={style.avatar_info}>
                {result}</div>
        </div>
    );
};


export default SubscribeView;


  