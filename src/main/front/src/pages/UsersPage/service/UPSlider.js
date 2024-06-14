import { createRef, useEffect, useRef, useState } from "react";
import {useDispatch, useSelector} from "react-redux";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { GrFormNext } from "react-icons/gr";
import { GrFormPrevious } from "react-icons/gr";
import {updatePromo} from "../../../components/redux/slices/promoSlice";
import imgdefsrc from "../../../assets/promo_1.jpg";
import {updatePromoUsers} from "../../../components/redux/slices/usersPageSlice";

const UPSlider = () => {
  const userPage = useSelector((state) => state.userspages);
  const style = setupStyles("sliderPromo")
  const [slide, setSlide] = useState(0);
  const [autoPlay, setAutuPlay] = useState(5000)
  const refSliderContain = createRef();
  const refSlide = createRef();
  const dispatch = useDispatch();
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

  useEffect(() => {
      // dispatch(updatePromoUsers(userPage.promoList));
  }, []);
  return (
    <>
      <div className={style.promo_contain} ref={refSliderContain}>
        <div className={style.promo_img}>


          {userPage.blobsPromoList && userPage.blobsPromoList.length > 0 ? (
              userPage.blobsPromoList.map((element, i) => (
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
        </div>
      </div>
    </>
  )
}
export default UPSlider;