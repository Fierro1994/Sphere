import setupStyles from "../../stylesModules/setupStyles";
import ImgCropper from "./ImgCropper";

const ImgEditor = ({image}) => {
    const style = setupStyles("galerypagestyle")
  return (
  <>
  <div className={style.gallery_controls}>
    

  </div>
  <div className={style.gallery_crop_container}>
    <ImgCropper image={image}/>
  </div>
  </>
  );
};

export default ImgEditor;