import {useDispatch, useSelector} from "react-redux";
import setupStyles from "../../pages/stylesModules/setupStyles";
import {addFriendSubscribe, getFriendsList, searchFriends,} from "../../../src/components/redux/slices/friendsSlice";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

const FriendsView = () => {
    const PATH = "http://localhost:3000/avatar"

    const style = setupStyles("friendsPage")
    const auth = useSelector((state) => state.auth);
    const friendsSl = useSelector((state) => state.friendsred);
    const [showPop, setShowPop] = useState(false)
    const dispatch = useDispatch()

    function resultsrc(el) {
        return PATH + "/" + el.userId + "/" + el.avatar
    }

    function dateFormat(time) {
        let time_day = "";
        let day = time[2];
        let mounth = time[1];

        const now = new Date();

        if (day < 10) day = "0" + day
        if (mounth < 10) mounth = "0" + mounth

        if (day !== now.getDay()) {
            time_day = day + "-" + mounth + " в "
        }
        var dateFormat = "Был(а): " + time_day + " " + time[3] + ":" + time[4]
        return dateFormat
    }

    useEffect(() => {
        dispatch(getFriendsList(auth._id))
    }, []);


    function addFriendHandler(user) {

    }


    function openProfileHandler(userId) {

    }


    const result =
        friendsSl.friendsList?.map((el, i) => {
            return (
                <div key={i} className={style.search_avatar} style={{
                    left: i * 10 + "vw",
                }}>
                    <div>
                        <img onClick={() => setShowPop(!showPop)} className={style.avatar_img} src={resultsrc(el)}/>
                        <div className={style.name_p}><p className={style.name}>{el.firstName + " " + el.lastName}</p>
                        </div>
                        <div className={style.online_p}><p className={style.name}>{dateFormat(el.lastTimeOnline)}</p>
                        </div>
                    </div>
                    <div className={!showPop ? style.popup_cont : style.popup_cont + " " + style.open}>
                        <div>
                            <Link title="Друзья" className={style.btn_pop} to={"/app/friends/" + el.userId}>Открыть
                                профиль </Link>

                            <span className={style.btn_pop} onClick={() => addFriendHandler(el.userId)}>Удалить из друзей</span>
                        </div>

                    </div>
                </div>
            )

        })

    return (
        <div className={style.tablemain}>
            <h2>Мои друзья</h2>

            <form>
                <p>
                    <input type="search" name="q" className={style.search_input}
                           placeholder="Введите имя или id друга"/>
                    <input type="submit" className={style.search_submit} value="Найти"/></p>
            </form>
            <div className={style.display_info}><p>Всего найдено: {friendsSl.friendsList?.length}</p></div>

            {result}

        </div>
    )
}

export default FriendsView;


  