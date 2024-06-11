import { createRef, useEffect, useState } from "react";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { useDispatch, useSelector } from "react-redux";
import { Rnd } from "react-rnd";
import { BsArrows } from "react-icons/bs";
import { FaArrowsAlt } from "react-icons/fa";
import { uploadMoments } from "../../redux/slices/momentsSlice";
import { MdOutlineClose } from "react-icons/md";

const VideoEditor = ({ videosrc }) => {
    const style = setupStyles("actualMP")
    const auth = useSelector((state) => state.auth);
    const moments = useSelector((state) => state.moments);
    const [widthVideo, setwidthVideo] = useState("100%")
    const [widthBlockVideo, setwidthBlockVideo] = useState(null)
    const [xpos, setxpos] = useState(0)
    const [ypos, setypos] = useState(0)
    const [stop, setStop] = useState(true);
    const [current, setCurrent] = useState(null)
    const [duration, setDuration] = useState(null)
    const [progress, setProgress] = useState("0%");
    const [enabled, setEnabled] = useState(false)
    const [widthTimeZone, setWidthTimeZone] = useState(null)
    const [startTrim, setstartTrim] = useState(null)
    const [endtTrim, setendtTrim] = useState(null)
    const [filedef, setFiledef] = useState(null);
    const [file, setFile] = useState(null);
    const refTimeZone = createRef();
    const canvasRef = createRef();
    const progressRef = createRef();
    const trimRef = createRef();
    const videoRef = createRef();

    const dispatch = useDispatch()

    useEffect(() => {
        setFiledef(videosrc)
        setFile(URL.createObjectURL(videosrc));
    }, []);

    useEffect(() => {
        if (moments.isUplPen){
              
    setFiledef(null)
    setwidthVideo("100%")
    setxpos(0)
    setProgress(0)
    setstartTrim(0)
    setendtTrim(0)
    setCurrent(0)
    setDuration(0)
        } 
     }, [moments.isUplPen]);
  

    function formatTime(time) {
        var minutes = Math.floor(time / 60);
        var seconds = Math.floor(time - minutes * 60);
        var minutesVal = minutes;
        var secondsVal = seconds;
        if (minutes < 10) {
            minutesVal = '0' + minutes;
        }
        if (seconds < 10) {
            secondsVal = '0' + seconds;
        }
        return minutesVal + ':' + secondsVal;
    }

    const onPlayV = (e) => {

        setDuration(videoRef.current.duration)
        if (((60 / Math.floor(videoRef.current.duration)) * 100) > 100) {
            setwidthBlockVideo(100 + "%")
            setwidthVideo(100 + "%")
        } else {
            setwidthBlockVideo((60 / Math.floor(videoRef.current.duration)) * 100 + "%")
            setwidthVideo((60 / Math.floor(videoRef.current.duration)) * 100 + "%")
        }
        setWidthTimeZone(refTimeZone.current.getBoundingClientRect().width)

    }
    const timeUpdate = (e) => {
        setCurrent(videoRef.current.currentTime)
        setProgress((videoRef.current.currentTime / videoRef.current.duration) * 100 + "%")
        var end = parseFloat(duration) / 100 * (parseFloat(xpos / widthTimeZone * 100) + parseFloat(widthVideo))
        var start = parseInt(duration) * (parseInt(xpos) / parseInt(widthTimeZone))
        setstartTrim(start)
        setendtTrim(end)
        if (enabled) {
            videoRef.current.currentTime = (xpos / widthTimeZone) * duration
            setEnabled(false)
        }
        if (videoRef.current.currentTime > end || videoRef.current.currentTime < start) {
            videoRef.current.currentTime = (xpos / widthTimeZone) * duration
        }

    }

    const onClickTime = (e) => {
        var pl = refTimeZone.current.getBoundingClientRect().left
        videoRef.current.currentTime = ((e.pageX - pl) / widthTimeZone) * duration
    }

    const coord = (e) => {
        setEnabled(true)
        setstartTrim(duration * (xpos / widthTimeZone));
        setendtTrim((duration / 100) * ((duration * (xpos / widthTimeZone) / duration) * 100 + parseFloat(widthVideo)))
    }


    const handlePlay = () => {

        setStop(!stop);
        if (stop === true) {
            videoRef.current.pause();
        } else {
            videoRef.current.play();
        }
    };

    const onSumbit = (e) => {
        const myData = new FormData();
        myData.append('file', filedef);
        myData.append('id', auth._id);
        myData.append('startTrim', startTrim);
        myData.append('endTrim', endtTrim);
        dispatch(uploadMoments(myData))
    }

    return (
        <>


           
                {filedef &&
                
                <button type="submit" className={style.save_btn}
                    onClick={(e) => {
                        e.preventDefault()
                        onSumbit()
                        setFiledef(null)
                        setwidthVideo("100%")
                        setxpos(0)
                        setProgress(0)
                        setstartTrim(0)
                        setendtTrim(0)
                        setCurrent(0)
                        setDuration(0)
                    }}
                >
                    Сохранить
                </button>
               
                 
                }

            <video onTimeUpdate={timeUpdate} onLoadedMetadata={onPlayV} onClick={handlePlay} ref={videoRef} className={style.video_editing} autoPlay loop>  <source src={URL.createObjectURL(videosrc)} type="video/mp4" ></source></video>

            <span className={style.time}>{formatTime(current)} / {formatTime(duration)}</span>
            <div className={style.trim_zone} ref={refTimeZone} onClick={onClickTime}>
                <div ref={progressRef} className={style.progress} style={{ width: progress }} ></div>
                <div className={style.rnd}>
                    <Rnd
                        enableResizing={{ top: false, right: true, bottom: false, left: false, topRight: false, bottomRight: false, bottomLeft: false, topLeft: false }}
                        dragAxis="x"
                        className={style.trim_res}
                        bounds={"parent"}
                        minWidth={"10%"}
                        maxWidth={widthBlockVideo}
                        size={{ width: widthVideo, height: "100%" }}
                        ref={trimRef}
                        position={{ x: xpos, y: ypos }}
                        dragGrid={[5, 5]}
                        onDrag={(e, d) => { setxpos(d.x); }}
                        onDragStop={(e, d) => {
                            coord()
                        }}
                        onResizeStop={(e, direction, ref, delta, position) => {
                            setwidthVideo(ref.style.width)
                        }}>
                        <BsArrows style={{ fontSize: "1.5vw", color: "#8f8f8f", position: "absolute", right: "-0.8vw", top: "0.2vw" }} />
                        <FaArrowsAlt style={{ fontSize: "1.3vw", color: "#8f8f8f", position: "absolute", top: "0.1vw" }} />

                    </Rnd>
                </div>

            </div>


        </>
    )
}
export default VideoEditor;






