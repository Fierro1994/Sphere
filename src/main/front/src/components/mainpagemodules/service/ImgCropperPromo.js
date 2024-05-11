// import { createRef, useCallback, useState } from "react";
// import Cropper from "react-easy-crop";
// import getCroppedImg from "../../../pages/GaleryPage/GalleryAddPage/Crop";
// import setupStyles from "../../../pages/stylesModules/setupStyles";
// import { useDispatch, useSelector } from "react-redux";
// import { uploadPromo } from "../../redux/slices/promoSlice";

// const ImgCropperPromo = ({image, nameimg, sizeimg}) => {
//   const [showCropper, setShowCropper] = useState(true)
//   const [crop, setCrop] = useState({ x: 0, y: 0 });
//   const [zoom, setZoom] = useState(1);
//   const [rotation, setRotation] = useState(0);
//   const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);
//   const [croppedImage, setCroppedImage] = useState(null);
//   const [uploadImage, setUploadImage] = useState(image);


//   const auth = useSelector((state) => state.auth);
//   const style = setupStyles("sliderPromo")
// const dispatch = useDispatch()

//   const onCropComplete = useCallback((croppedArea, croppedAreaPixels) => {
//     setCroppedAreaPixels(croppedAreaPixels);
//   }, []);

  

//   const showCroppedImage = useCallback(async () => {
//     try {
//       const croppedImage = await getCroppedImg(
//         uploadImage,
//         croppedAreaPixels,
//         rotation
//       );
//       setCroppedImage(croppedImage);
//       setShowCropper(false)
//     } catch (e) {
//       console.error(e);
//     }
//   }, [croppedAreaPixels, rotation, uploadImage]);


//   const onClose = useCallback(() => {
//     setShowCropper(true)
//     setCroppedImage(null);
//   }, []);
//   var file = new File([croppedImage], "my_image.jpeg",{type:"image/jpeg", lastModified:new Date().getTime()})
  
//   const onSumbit = (e)  => {
  
//     const myData = new FormData();
//     myData.append('file', croppedImage);
//     myData.append('id', auth._id);
//     myData.append("name", nameimg)
//     myData.append("size", sizeimg)
//      dispatch(uploadPromo(myData))
//   }
 

//   return (
 
//   );
// };

// export default ImgCropperPromo;