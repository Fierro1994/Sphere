import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../stylesModules/setupStyles";
import { searchFriends, updateFriendsList } from "../../components/redux/slices/friendsSlice";
import { useEffect } from "react";

const FriendsView =() => {

  const style = setupStyles("friendsPage")
  const auth = useSelector((state) => state.auth);
  const friendsSl = useSelector((state) => state.friendsred);
const dispatch = useDispatch()



useEffect(() => {
  dispatch(updateFriendsList(auth.email));

}, []);
  
  const result =  () =>{
   return( <></>)

  }
  return (
<>
<h2>Друзья</h2>
</>
   )
}

export default FriendsView;


  