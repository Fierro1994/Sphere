import { createRef, useEffect, useState } from "react";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { useDispatch, useSelector } from "react-redux";
import { endMoment } from "../../redux/slices/momentsSlice";


const ActualPlayer = ({videosrc}) => {
   const style = setupStyles("actualMP")
   const auth = useSelector((state) => state.auth);
   const moments = useSelector((state) => state.moments);
   const dispatch = useDispatch()
   const [current, setCurrent] = useState(null)
   const [duration, setDuration] = useState(null)
   const [progress, setProgress] = useState("0%");
   const refTimeZone = createRef();
   const progressRef = createRef();
   const progressImgRef = createRef();
   
   const trimRef = createRef();
   const videoRef = createRef();
   const PATH = "http://localhost:3000/moments"
   const [format,setFormat] = useState();

   useEffect(() => {
     setFormat(videosrc.substring(videosrc.indexOf('.')+1))
   }, [videosrc]);


   const onPlayV = (e) => {
      setDuration(videoRef.current.duration)
   }


   const timeUpdate = (e) => {
      
  
      if ((videoRef.current.duration - videoRef.current.currentTime ) < 0.3) {
         dispatch(endMoment())
      }
      setProgress((videoRef.current.currentTime / videoRef.current.duration) * 100 + "%")   
   }


   const handlePlay = () => {
      if (videoRef.current.paused) {
         videoRef.current.play();
      } else {
         videoRef.current.pause();
      }
   };

   
   function result(el) {
    return PATH + "/" + auth._id + "/" + el 
 }

 const [diffS, setDiff] = useState(0);
 const [tick, setTick] = useState(false);


 useEffect(()=> {
   if(diffS == 100 && format ==="jpeg"){
      dispatch(endMoment())
   }
   if (diffS == 100) return 
   if (format === "jpeg") {
      setProgress(diffS+"%")
   }
   setDiff(
    diffS+0.5
   ) 
 }, [tick])
     
 useEffect(()=>{
   const timerID = setInterval(() => setTick(!tick), 20);
   return () => clearInterval(timerID);
 }, [tick])

 function endVideo(){
   dispatch(endMoment())
 }

   return (
      <>
  {format === "jpeg" && 
 <>
 <div className={style.actual_player_show}>
   <img  src={result(videosrc)}></img></div>
  <div className={style.act_pr_zone} ref={refTimeZone}>
    <div ref={progressImgRef} className={style.progress_act} style={{ width: progress }} ></div></div>
    </>
  }
      {format === "mp4" && 
      <>
    <video type="video/mp4" onClick={handlePlay}  onTimeUpdate={timeUpdate}  src={result(videosrc)} className={style.videoc} ref={videoRef} autoPlay loop></video>
    <div className={style.act_pr_zone} ref={refTimeZone}>
    <div ref={progressRef} className={style.progress_act} style={{ width: progress }} ></div></div>
    </>
}
    </>
)}

export default ActualPlayer;






