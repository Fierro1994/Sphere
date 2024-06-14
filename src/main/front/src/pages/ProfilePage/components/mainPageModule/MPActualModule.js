import { useCallback, useEffect, useState } from "react";
import setupStyles from "../../../stylesModules/setupStyles";
import { useDispatch, useSelector } from "react-redux";
import { deleteMoments, startMoment, updateMoments, uploadMomImage, uploadMoments } from "../../../../components/redux/slices/momentsSlice";
import getCroppedImg, {base64ToFile} from "../../../GaleryPage/GalleryAddPage/Crop";
import Cropper from "react-easy-crop";
import { HiOutlineDotsVertical } from "react-icons/hi";
import { MdOutlineClose } from "react-icons/md";
import { GrFormNext, GrFormPrevious } from "react-icons/gr";
import ActualPlayer from "../service/ActualPlayer";
import VideoEditor from "../service/VideoEditor";


const MPActualModule = () => {
   const style = setupStyles("actualMP")
   const [popShow, setPopShow] = useState(false)
   const [file, setFile] = useState(null);
   const [fileData, setFileData] = useState(null);
   const auth = useSelector((state) => state.auth);
   const moments = useSelector((state) => state.moments);
   const [showCropper, setShowCropper] = useState(true)
   const [crop, setCrop] = useState({ x: 0, y: 0 });
   const [zoom, setZoom] = useState(1);
   const [rotation, setRotation] = useState(0);
   const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);
   const [croppedImage, setCroppedImage] = useState(null);
   const [showCtrl, setShowCtrl] = useState(false)
   const [actV, setActV] = useState(null)
   const [showPlayer, setShowPlayer] = useState(true)
   const dispatch = useDispatch()

   const handleImageUpload = async (e) => {
      setFileData(e.target.files[0])
      setFile(URL.createObjectURL(e.target.files[0]));

   };


   useEffect(() => {
      if (moments.isUplPen) {
         setFile(null)
         setFileData(null)
         setShowPlayer(true)
         setActV(moments.momentsList.length === 0 ? 0 : 0);
      }
   }, [moments.isUplPen]);

   useEffect(() => {
      if(moments.isUpdFull === false){
         dispatch(updateMoments());
      }
   }, [moments.isUpdFull]);

   useEffect(() => {
      setActV(0);
   }, []);

   useEffect(() => {
     if (moments.isEndMoments) {
      setActV(actV === moments.momentsList.length - 1 ? 0 : actV + 1);
      setShowCtrl(false)
      dispatch(startMoment())
     }
   }, [moments?.isEndMoments]);

   useEffect(() => {
      const timeoutId = setTimeout(() => {
         setShowCtrl(false)
      }, 3000);
      return () => clearTimeout(timeoutId);
   }, [showCtrl]);


   const onCropComplete = useCallback((croppedArea, croppedAreaPixels) => {
      setCroppedAreaPixels(croppedAreaPixels);
   }, []);

   const showCroppedImage = useCallback(async () => {
      try {
         const croppedImage = await getCroppedImg(
            file,
            croppedAreaPixels,
            rotation
         );
         setCroppedImage(croppedImage);
         setShowCropper(false)
      } catch (e) {
         console.error(e);
      }
   }, [croppedAreaPixels, rotation, file]);


   const nextAct = () => {
      setActV(actV === moments.momentsList.length - 1 ? 0 : actV + 1);
      setShowCtrl(false)
   };
   const prevAct = () => {
      setActV(actV === 0 ? moments.momentsList.length - 1 : actV - 1);
      setShowCtrl(false)
   };
   const onDelete = () => {
      const myData = new FormData();
      moments.momentsList.map((element, i) => {
         if (i === actV) {
            myData.append('key', element);
         }
      })
      myData.append('id', auth._id);
      dispatch(deleteMoments(myData))
      dispatch(updateMoments(auth._id));
      if (moments.isPendDel == false) {
         setActV(moments.momentsList.length === 0 ? 0 : 0);
      }
   }
   const onSumbit = (e) => {
      const myData = new FormData();
      const file = base64ToFile(croppedImage, fileData.name)
      myData.append('file', file);
      dispatch(uploadMomImage(myData))    
  }

   return (
      <>
         <div className={style.actual}>
            <div className={style.actual_circle} onClick={e => {
               setPopShow(true)
               setShowPlayer(true)
            }}></div>
            <div className={!popShow ? style.pop_cont : style.pop_cont + " " + style.open}>

               {fileData && fileData.type === "video/mp4" &&
                  <div className={style.editor_show}>
                     <VideoEditor videosrc={fileData} />
                  </div>
               }
               {showPlayer && <div className={style.actual_show}>
                  <div className={style.crop_container_cropped}>
                  <span className={style.circle_act_items}>{moments.momentsList.map((element, i) => {
                        return (
                           <span key={i} onClick={ ()=> setActV(i)} className={actV !== i ? style.circle_items : style.circle_items_this}>
                              
                           </span>
                        )
                     })}</span>
                     {moments.isUplFul && moments.momentsList.map((element, i) => {

                        return (
                           <span key={i}>
                              
                              {actV === i && <ActualPlayer videosrc={element}  />}
                           </span>
                        )
                     })}
                  </div>
               </div>}
               
               {fileData && fileData.type === "image/jpeg" && !croppedImage &&

                  <div className={style.actual_show}>
                     <Cropper
                        image={file}
                        crop={crop}
                        rotation={rotation}
                        zoom={zoom}
                        zoomSpeed={0.5}
                        maxZoom={3}
                        zoomWithScroll={true}
                        showGrid={true}
                        aspect={3 / 4}
                        onCropChange={setCrop}
                        onCropComplete={onCropComplete}
                        onZoomChange={setZoom}
                        onRotationChange={setRotation}
                     />
                  </div>}
                  {croppedImage && (
                        <div className={style.cropcontainer_cropped}>
                           <img className={style.promo_img} src={croppedImage} alt="cropped" />
                        </div>
                     )}

{croppedImage && <button type="submit" className={style.saveimg_btn}
                    onClick={(e) => {
                        e.preventDefault()
                        onSumbit()
                       setCroppedImage(null)
                       setFile(null)
                       setFileData(null)
                        
                    }}
                >
                    Сохранить
                </button>}
               
                  {fileData && fileData.type === "image/jpeg" && !croppedImage && <button className={style.cut_btn}
                     onClick={(e) => {
                        e.preventDefault()
                        showCroppedImage()
                     }}>
                     Далее
                  </button>}   


               {showPlayer && <span className={style.arrow_prev_cont} onClick={prevAct} ><GrFormPrevious className={style.arrow_prev} />   </span>}
               {showPlayer && <span className={style.arrow_next_cont} onClick={nextAct} ><GrFormNext className={style.arrow_next} /> </span>}
               {showPlayer && <HiOutlineDotsVertical className={style.ctrl_dots} onClick={(e) => { setShowCtrl(!showCtrl) }} />}
               {popShow && <MdOutlineClose className={style.close_svg} onClick={(e) => {
                  e.preventDefault()
                  setCroppedImage(null)
                  setPopShow(false)
                  setFile(null)
                  setFileData(null)
                  setShowCtrl(false)
                  setShowPlayer(false)
                  setActV(0)
               }} />}
               <div className={!showCtrl ? style.actual_controls : style.actual_controls + " " + style.open}>
                  {!file &&
                     <label className={style.download_btn}>
                        Загрузить
                        <input
                           type="file"
                           name="cover"
                           onChange={handleImageUpload}
                           accept="img/jpeg, video/, img/jpg"
                           style={{ display: "none" }}
                           onClick={() => setShowPlayer(false)}
                        />
                     </label>
                  }
                  {!fileData && <label className={style.close_btn} onClick={(e) => {
                     e.preventDefault()
                     onDelete()
                  }}>Удалить</label>}
               </div>
            </div>
         </div>
      </>

   );
}

export default MPActualModule;






