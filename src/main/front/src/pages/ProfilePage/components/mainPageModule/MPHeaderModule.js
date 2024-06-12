import { createRef, useCallback, useEffect, useState } from "react"
import setupStyles from "../../../stylesModules/setupStyles"
import { useDispatch, useSelector } from "react-redux";
import getCroppedImg, { base64ToFile } from "../../../GaleryPage/GalleryAddPage/Crop";
import { deleteHeaderFile, updateHeader, uploadHeader } from "../../../../components/redux/slices/headerSlice";
import { GrFormNext, GrFormPrevious } from "react-icons/gr";
import Cropper from "react-easy-crop";
import Comment_avatar from "../service/Comment_avatar";
import InfoModule from "./MPInfoModule"
import imgdefsrc from "../../../../assets/defavatar.jpg"

const MPHeaderModule = ({ info }) => {
   const auth = useSelector((state) => state.auth);
   const header = useSelector((state) => state.header);
   const [infomod, setinfoMod] = useState(null);
   const [infomod2, setinfoMod2] = useState(null);
   const style = setupStyles("headerMP")
   const [showPopup, setShowPopup] = useState(true)
   const [image, setImage] = useState(null);
   const [nameImg, setNameImage] = useState(null);
   const [sizeImg, setSizeImage] = useState(null);
   const refSlide = createRef();
   const [slide, setSlide] = useState(0);
   const dispatch = useDispatch()
   const [showCropper, setShowCropper] = useState(true)
   const [crop, setCrop] = useState({ x: 0, y: 0 });
   const [zoom, setZoom] = useState(1);
   const [rotation, setRotation] = useState(0);
   const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);
   const [croppedImage, setCroppedImage] = useState(null);
   const handleImageUpload = async (e) => {
      setImage(URL.createObjectURL(e.target.files[0]));
      setNameImage((e.target.files[0].name));
      setSizeImage((e.target.files[0].size));
   };

   const nextSlide = () => {
      setSlide(slide === header.headerList.length - 1 ? 0 : slide + 1);

   };
   const prevSlide = () => {

      setSlide(slide === 0 ? header.headerList.length - 1 : slide - 1);
   };


   const onDelete = () => {

      const myData = new FormData();
      header.headerList.map((element, i) => {
         if (i === slide) {
            myData.append('key', element);
         }
      })
      myData.append('id', auth._id);
      dispatch(deleteHeaderFile(myData))

      dispatch(updateHeader(auth._id));
      if (header.isPendDel == false) {
         setSlide(header.headerList.length === 0 ? 0 : 0);

      }

   }

   useEffect(() => {
      if (header.isUpdFull === false) {
         dispatch(updateHeader(auth._id));
      }
   }, [header.isUpdFull]);

   useEffect(() => {
      if (header.isUpdFull === true) {
        setSlide(header.headerList.length - 1)
      }
   }, [header.isUpdFull]);


   useEffect(() => {
      if (info !== null) {
         setinfoMod(<InfoModule block={1} />)
         setinfoMod2(<InfoModule block={2} />)
      }
   }, []);



   const onCropComplete = useCallback((croppedArea, croppedAreaPixels) => {
      setCroppedAreaPixels(croppedAreaPixels);
   }, []);

   const showCroppedImage = useCallback(async () => {
      try {
         const croppedImage = await getCroppedImg(
            image,
            croppedAreaPixels,
            rotation
         );
         setCroppedImage(croppedImage);
         setShowCropper(false)
      } catch (e) {
         console.error(e);
      }
   }, [croppedAreaPixels, rotation, image]);


   const onSumbit = (e) => {
      const file = base64ToFile(croppedImage, nameImg)
      const myData = new FormData();
      myData.append('avatar', file);
      dispatch(uploadHeader(myData))
   }




   return (
      <>

         <div className={style.header}>
            {infomod}
            <div className={style.head_cont}>
               <div className={style.header_main} onClick={function () {
                  setShowPopup(!showPopup)
               }}>
                  <div className={style.header_button_container} >
                     {header.headerList && header.headerList.length > 0 ? (<img className={style.promo_img} src={header.headerList[header.headerList.length - 1]} ref={refSlide}></img>) :
                        (<img className={style.promo_img} src={imgdefsrc} ref={refSlide}></img>)}
                  </div>
               </div>

            </div>

            <div className={showPopup ? style.popup_header : style.popup_header + " " + style.open}>
               <Comment_avatar />
               <div className={style.popup_header_avatar}>
                  {image && <div className={style.crop_container}>
                     {!croppedImage && <Cropper
                        image={image}
                        crop={crop}
                        rotation={rotation}
                        zoom={zoom}
                        zoomSpeed={0.5}
                        maxZoom={3}
                        zoomWithScroll={true}
                        showGrid={true}
                        aspect={8 / 8}
                        onCropChange={setCrop}
                        onCropComplete={onCropComplete}
                        onZoomChange={setZoom}
                        onRotationChange={setRotation}
                     />}

                  </div>
                  }
                  {croppedImage && (
                     <div className={style.crop_container_cropped}>
                        <img className={style.promo_img} src={croppedImage} alt="cropped" />
                     </div>
                  )}




                  {!image &&
                     <>
                        <div className={style.popup_quad_shadow}>

                           {header.isPendDel && <div className={style.loader_container}><p className={style.loader}></p></div>}
                        </div>

                        <button onClick={(e) => {
                           e.preventDefault()
                           prevSlide()
                        }} className={style.previos}><GrFormPrevious /></button>

                        <button onClick={(e) => {
                           e.preventDefault()
                           nextSlide()
                        }} className={style.next}><GrFormNext /></button>
                        {header.headerList && header.headerList.length > 0 ? 
                       (header.headerList.map((element, i) => {
                           return (
                           <img className={slide === i ? style.promo_img : style.promo_img_hidden} src={element} key={i} ref={refSlide}></img>
                           )})) : (<img className={style.promo_img} src={imgdefsrc}  ref={refSlide}></img>)}
                     </>
                  }

               </div>

               <div className={style.btn_line}>
                  {image && !croppedImage && <button className={style.download_btn}
                     onClick={(e) => {
                        e.preventDefault()
                        showCroppedImage()
                     }}
                  >
                     показать
                  </button>}
                  {croppedImage &&
                     <button type="submit" className={style.download_btn}
                        onClick={(e) => {
                           e.preventDefault()
                           onSumbit()
                           setCroppedImage(null)
                           setImage(null)
                           setShowPopup(true)
                        }}
                     >
                        сохранить
                     </button>
                  }
                  {!image &&
                     <label className={style.download_btn}>
                        загрузить
                        <input
                           type="file"
                           name="cover"
                           onChange={handleImageUpload}
                           accept="img/*"
                           style={{ display: "none" }}
                        />
                     </label>
                  }
                  {!image && <label className={style.exit_btn} onClick={(e) => {
                     e.preventDefault()
                     onDelete()
                  }}>Удалить</label>}
                  <label className={style.exit_btn}
                     onClick={(e) => {
                        e.preventDefault()
                        setImage(null)
                        setCroppedImage(null)
                        setShowPopup(true)
                        setSlide(header.headerList.length - 1)
                     }}
                  >Закрыть</label>
               </div>
            </div>
            {infomod2}
         </div>

      </>

   );
}

export default MPHeaderModule; 
