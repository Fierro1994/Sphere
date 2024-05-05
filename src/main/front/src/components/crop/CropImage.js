import setupStyles from "../../stylesModules/setupStyles";
import MainPageSetInfo from "./MainPageSetInfo";
import { useEffect, useState } from "react";
import { FaPencil } from "react-icons/fa6";
import { FaRegTrashCan } from "react-icons/fa6";
import Cropper from 'react-easy-crop'
import getCroppedImg from "./canvasUtils";
const CROP_AREA_ASPECT = 6 / 1.5;

const Output = ({ croppedArea, src }) => {
   const style = setupStyles("mainpagesetstyle")
   const scale = 100 / croppedArea.width;
   const transform = {
     x: `${-croppedArea.x * scale}%`,
     y: `${-croppedArea.y * scale}%`,
     scale,
     width: "calc(100% + 0.5px)",
     height: "auto"
   };
 
   const imageStyle = {
     transform: `translate3d(${transform.x}, ${transform.y}, 0) scale3d(${transform.scale},${transform.scale},1)`,
     width: transform.width,
     height: transform.height
   };
 
   return (
     <div
       className={style.output}
       style={{ paddingBottom: `${100 / CROP_AREA_ASPECT}%` }}
     >
       <img src={src} alt="" style={imageStyle} />
     </div>
   );
 };

 function readFile(file) {
   return new Promise((resolve) => {
     const reader = new FileReader()
     reader.addEventListener('load', () => resolve(reader.result), false)
     reader.readAsDataURL(file)
   })
 }
const CropImage = () => {

   const [imageSrc, setImageSrc] = useState(null)
   const [crop, setCrop] = useState({ x: 0, y: 0 });
   const [zoom, setZoom] = useState(1);
   const [croppedAreaPixels, setCroppedAreaPixels] = useState(null)
   const [croppedImage, setCroppedImage] = useState(null)
   const [croppedArea, setCroppedArea] = useState(null);

   const onCropComplete = (croppedArea, croppedAreaPixels) => {
      setCroppedAreaPixels(croppedAreaPixels)
    }
  


   const showCroppedImage = async () => {
      try {
        const croppedImage = await getCroppedImg(
          imageSrc,
          croppedAreaPixels
        )
        setCroppedImage(croppedImage)
      } catch (e) {
        console.error(e)
      }
    }

    const onClose = () => {
      setCroppedImage(null)
    }
 

    const onFileChange = async (e) => {
      if (e.target.files && e.target.files.length > 0) {
        const file = e.target.files[0]
        let imageDataUrl = await readFile(file)
  
        setImageSrc(imageDataUrl)
      }
    }
 
   const style = setupStyles("mainpagesetstyle")
   var listMenuModules = []
   if (localStorage.getItem("mainPageModules")) {
      listMenuModules = JSON.parse(localStorage.getItem("mainPageModules"))
    }
    const [enabled, setEnabled] = useState(false) 
    const [show, setShow] = useState(false) 
    useEffect(() => {
      listMenuModules.map((element, i) => {
         if(element.name === "HEADER" && element.isEnabled === true)
         {
            setEnabled(true)
         }
         
      })
    }, []);
    return (
      <>
      
      <div className={!show ? style.popup_header : style.popup_header + " " + style.open}>
               <p>настройки фона</p>
          
               <a className={style.popup_a}></a>
               <div>
      {imageSrc ? (
        <div>
          <div className={style.containercrop}>
            <Cropper
              image={imageSrc}
              crop={crop}
              zoom={zoom}
              aspect={CROP_AREA_ASPECT}
              onCropChange={setCrop}
              onZoomChange={setZoom}
              onCropComplete={onCropComplete}
              onCropAreaChange={setCroppedArea}
            />
          </div>
          <button onClick={showCroppedImage}> Show Result</button>
          

        </div>
      ) : (
        <input type="file" onChange={onFileChange} accept="image/*" />
      )}
    </div>
         </div>
      <div className={style.header}>
      <div className={style.viewer}>
        <div>{croppedArea && <Output croppedArea={croppedArea} src={imageSrc} />}</div>
      </div>
      
         <div className={style.header_main}> 
            <p>Шапка главной страницы</p>
            {enabled? <div className={style.header_button_container}><button className={style.correct} onClick={function () {
            setShow(!show)
          }}><FaPencil /></button><button className={style.correct}><FaRegTrashCan color={"#910b0b"} /></button></div>  :    <button className={style.plus}>+</button>}
   
      
      
      </div>
      <MainPageSetInfo/>
   </div>
      
      </>

         );
}
 
export default CropImage; 