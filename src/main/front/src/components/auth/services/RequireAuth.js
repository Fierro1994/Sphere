import { useDispatch, useSelector } from "react-redux";
import verifyToken from "./verifyToken";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { setLastTimeOnline } from "../../redux/slices/userOnlineSlice";

 function RequireAuth({ children }) {
    const navigate = useNavigate()
    const auth = useSelector((state) => state.auth);
    const dispatch = useDispatch()

    useEffect(() => {
      dispatch(setLastTimeOnline(auth._id))
    },[])
    
    useEffect(() => {
    if (localStorage.getItem("access")){
      verifyToken(auth.token)
      if(verifyToken(auth.token) === false){
        navigate("/")
      }
      }if(!localStorage.getItem("access")){navigate("/") }
  },[auth])
    return children;
  }
  export default RequireAuth;