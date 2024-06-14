import { createRef, useEffect, useState } from "react";
import setupStyles from "../../../stylesModules/setupStyles";
import { useDispatch, useSelector } from "react-redux";
import { endMoment } from "../../../../components/redux/slices/momentsSlice";


const ActualPlayer = ({videosrc}) => {
   const style = setupStyles("actualMP")
   const dispatch = useDispatch()
   const [progress, setProgress] = useState("0%");
   const refTimeZone = createRef();
   const progressRef = createRef();
   const progressImgRef = createRef();
   const videoRef = createRef();
   const [format,setFormat] = useState();

    useEffect(() => {
            setFormat(videosrc.type.split('/')[0])
    }, [videosrc]);



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



 const [diffS, setDiff] = useState(0);
 const [tick, setTick] = useState(false);


 useEffect(()=> {
   if(diffS == 100 && format ==="image"){
      dispatch(endMoment())
   }
   if (diffS == 100) return 
   if (format === "image") {
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
  {format === "image" &&
 <>
 <div className={style.actual_player_show}>
   <img  src={videosrc.blob}></img></div>
  <div className={style.act_pr_zone} ref={refTimeZone}>
    <div ref={progressImgRef} className={style.progress_act} style={{ width: progress }} ></div></div>
    </>
  }
      {format === "video" &&
      <>
    <video type="video/mp4" onClick={handlePlay}  onTimeUpdate={timeUpdate}  src={videosrc.blob} className={style.videoc} ref={videoRef} autoPlay loop></video>
    <div className={style.act_pr_zone} ref={refTimeZone}>
    <div ref={progressRef} className={style.progress_act} style={{ width: progress }} ></div></div>
    </>
}
    </>
)}

export default ActualPlayer;






