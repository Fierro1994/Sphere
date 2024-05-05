import { useCallback, useState } from "react";
import Cropper from "react-easy-crop";
import getCroppedImg from "./Crop";
import setupStyles from "../../stylesModules/setupStyles";
import { IoCutOutline } from "react-icons/io5";


const ImgCropper = ({image}) => {
  const [showCropper, setShowCropper] = useState(true)
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [rotation, setRotation] = useState(0);
  const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);
  const [croppedImage, setCroppedImage] = useState(null);
  const style = setupStyles("galerypagestyle")
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
      console.log("donee", { croppedImage });
      setCroppedImage(croppedImage);
      setShowCropper(false)
    } catch (e) {
      console.error(e);
    }
  }, [croppedAreaPixels, rotation, image]);

  const onClose = useCallback(() => {
    setShowCropper(true)
    setCroppedImage(null);
  }, []);

  return (
 <>
            <Cropper
              image={image}
              crop={crop}
              rotation={rotation}
              zoom={zoom}
              zoomSpeed={1}
              maxZoom={3}
              zoomWithScroll={true}
              showGrid={true}
              aspect={3 / 4}
              onCropChange={setCrop}
              onCropComplete={onCropComplete}
              onZoomChange={setZoom}
              onRotationChange={setRotation}
            />

      
            {croppedImage && (
              <img className={style.croppedImgContainer} src={croppedImage} alt="cropped" />
            )}
            
    


       

      



     



            </>
  );
};

export default ImgCropper;