import { createRef, useCallback, useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../../../stylesModules/setupStyles";
import { deletePromoFile, downloadImg, updatePromo, uploadPromo } from "../../../../components/redux/slices/promoSlice";
import { GrFormNext } from "react-icons/gr";
import { GrFormPrevious } from "react-icons/gr";
import getCroppedImg, { base64ToFile } from "../../../GaleryPage/GalleryAddPage/Crop";
import Cropper from "react-easy-crop";
import { BiDotsHorizontalRounded } from "react-icons/bi";
import imgdefsrc from "../../../../assets/promo_1.jpg"


const Slider = () => {
  const auth = useSelector((state) => state.auth);
  const promosl = useSelector((state) => state.promo);
  const style = setupStyles("sliderPromo")
  const dispatch = useDispatch()
  const [slide, setSlide] = useState(0);
  const [autoPlay, setAutuPlay] = useState(5000)
  const [showPopMenu, setShowPopMenu] = useState(false)
  const [showPopup, setShowPopup] = useState(false)
  const [image, setImage] = useState(null);
  const [nameImg, setNameImage] = useState(null);
  const [sizeImg, setSizeImage] = useState(null);
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

  const refSliderContain = createRef();
  const refSlide = createRef();

 
  const nextSlide = () => {
    setSlide(slide === promosl.promoList.length - 1 ? 0 : slide + 1);
    
   
  };
  const prevSlide = () => {

    setSlide(slide === 0 ? promosl.promoList.length - 1 : slide - 1);
  };

  async function useInterval(callback, delay) {
    const savedCallback = useRef();
    useEffect(() => {
      savedCallback.current = callback;
    }, [callback]);

    useEffect(() => {
      async function tick() {
       await savedCallback.current();
      }
      if (delay !== null) {
        let id = setInterval(tick, delay);
        return () => clearInterval(id);
      }
    }, [delay]);

  }

  useInterval(() => {
    setSlide(slide === promosl.promoList.length - 1 ? 0 : slide + 1)
  }, autoPlay);


  useEffect(() => {
    if(promosl.isUpdFul === false){
      dispatch(updatePromo(auth._id));  
    }
  }, [promosl.isUpdFul]);

   const onDelete = () => {
    
    const myData = new FormData();
    promosl.promoList.map((element, i) => {
      
      if (i===slide) {
        myData.append('key', element);
        
      }
    })
   dispatch(deletePromoFile(myData))
   dispatch(updatePromo(auth._id));
   if(promosl.isPendDel == false){
    setSlide(promosl.promoList.length === 0 ? 0 : 0);
   
   }
  }

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
    myData.append('file', file);
    dispatch(uploadPromo(myData))
  }

  const onPopupMenuOpen= (e) => {
    setShowPopMenu(!showPopMenu)
    
    let int = setInterval(() => {
      {
        setShowPopMenu(false)
      }
      clearInterval(int)

    }, 3000)
    
  }

  return (
    <>

      <div className={style.promo_contain} ref={refSliderContain}>
      <div className={style.promo_img}>
  <div className={style.dot} onClick={(e) => {
    setShowPopup(!showPopup)
  }}> <BiDotsHorizontalRounded/></div>
  {promosl.promoList && promosl.promoList.length > 0 ? (
    promosl.promoList.map((element, i) => (
      <img
        className={slide === i ? style.promo_img : style.promo_img_hidden}
        src={element}
        key={i}
        alt={element}
        ref={refSlide}
      ></img>
    ))
  ) : (
    <img className={style.promo_img} src={imgdefsrc} alt="default"></img>
  )}
</div>
        <div className={style.slider_control}
          onMouseEnter={(e) => {
            setAutuPlay(100000)
          }}
          onMouseLeave={(e) => {
            setAutuPlay(5000)
          }} >
          <button onClick={(e) => {
            e.preventDefault()
            prevSlide()
          }} className={style.previos}><GrFormPrevious /></button>

          <button onClick={(e) => {
            e.preventDefault()
            nextSlide()
          }} className={style.next}><GrFormNext /></button>
           <div className={!showPopup ? style.popup : style.popup + " " + style.open}>
           
            <div className={style.popup_container}>

            {!image && 
<>
            <button onClick={(e) => {
                     e.preventDefault()
                     prevSlide()
                  }} className={style.previos_small}><GrFormPrevious /></button>

                  <button onClick={(e) => {
                     e.preventDefault()
                     nextSlide()
                  }} className={style.next_small}><GrFormNext /></button>
        {promosl.promoList && promosl.promoList.length > 0 ? (
    promosl.promoList.map((element, i) => (
      <img
        className={slide === i ? style.promo_img : style.promo_img_hidden}
        src={element}
        key={i}
        alt={element}
        ref={refSlide}
      ></img>
    ))
  ) : (
    <img className={style.promo_img} src={imgdefsrc} alt="default"></img>
  )}</>
        }
      
                 {image &&  <div className={style.crop_container}>
{!croppedImage && <Cropper
                    image={image}
                    crop={crop}
                    rotation={rotation}
                    zoom={zoom}
                    zoomSpeed={1}
                    maxZoom={3}
                    zoomWithScroll={true}
                    showGrid={true}
                    aspect={16 / 3}
                    onCropChange={setCrop}
                    onCropComplete={onCropComplete}
                    onZoomChange={setZoom}
                    onRotationChange={setRotation}
                  />}
                  {croppedImage && (
                    <div className={style.crop_container_cropped}>
                    <img className={style.croppedImgContainer} src={croppedImage} alt="cropped" />
                    </div>
                  )}
                 
                </div>
              }
            
            <div className={style.btn_line}>
               {image && !croppedImage&& <button className={style.download_btn}
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
                              setShowPopup(false)
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
                        setShowPopup(false)
                        setSlide(promosl.promoList.length-1)
                     }}
                  >Закрыть</label>
               </div>
            </div>
          </div>
        </div>
      </div>


    </>
  )
}
export default Slider;