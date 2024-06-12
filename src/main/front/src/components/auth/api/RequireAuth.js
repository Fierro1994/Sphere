import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";



export const RequireAuth = ({ children }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = useSelector((state) => state.auth);

  useEffect(()=>{

    if(!localStorage.getItem("access")){
      
      navigate("/login")
  }

  }, [])


  return children;
};

