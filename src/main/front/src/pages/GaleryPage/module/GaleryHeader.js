import { useState } from "react"
import setupStyles from "../../stylesModules/setupStyles"
import { TbPhotoPlus } from "react-icons/tb";
import ImgEditor from "./ImgEditor";


const GaleryHeader = () => {

   const style = setupStyles("galerypagestyle")
   const [image, setImage] = useState(null);

   const handleImageUpload = async (e) => {
      console.log(e.target.files);
      setImage(URL.createObjectURL(e.target.files[0]));
   };

   return (
      <>
         <div className={style.galery_header}>
            <h2 className={style.namepage}>Галерея</h2>
            {!image ?
               <div className={style.gallery_controls}>
                  <label className={style.gallery_controls_btn}>
                     <TbPhotoPlus />
                     <input
                        type="file"
                        name="cover"
                        onChange={handleImageUpload}
                        accept="img/*"
                        style={{ display: "none" }}
                     />
                  </label>
               </div>
               :
               <div className={style.popup_container}>
                  <ImgEditor image={image} />
               </div>
            }
         </div>
      </>

   );
}

export default GaleryHeader; 
