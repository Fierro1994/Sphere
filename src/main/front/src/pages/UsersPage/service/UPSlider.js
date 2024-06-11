import { createRef, useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { GrFormNext } from "react-icons/gr";
import { GrFormPrevious } from "react-icons/gr";

const UPSlider = () => {
  const userPage = useSelector((state) => state.userspages);
  const style = setupStyles("sliderPromo")
  const PATH = "http://localhost:3000/imagepromo"
  const [slide, setSlide] = useState(0);
  const [autoPlay, setAutuPlay] = useState(5000)
  const refSliderContain = createRef();
  const refSlide = createRef();

  const nextSlide = () => {
    setSlide(slide === userPage.promoList.length - 1 ? 0 : slide + 1);
   
  };
  const prevSlide = () => {

    setSlide(slide === 0 ? userPage.promoList.length - 1 : slide - 1);
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
    setSlide(slide === userPage.promoList.length - 1 ? 0 : slide + 1)
  }, autoPlay);


 const result = userPage.promoList.map((element, i) => {
        if (element.name === "promo_1") {
            return  <img className={slide === i ? style.promo_img : style.promo_img_hidden} src={PATH + "/" + userPage.userId + "/" + element.name} key={i} ref={refSlide} alt={element.userId}></img> 
        } else return  <img className={slide === i ? style.promo_img : style.promo_img_hidden} src={PATH + "/" + userPage.userId + "/" + element.key} key={i} ref={refSlide} alt={element.userId}></img> 
    }) 
  
  return (
    <>
      <div className={style.promo_contain} ref={refSliderContain}>
        <div className={style.promo_img} >
             {result}
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
        </div>
      </div>
    </>
  )
}
export default UPSlider;