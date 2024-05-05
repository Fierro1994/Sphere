import style from './Promo.module.css';
import Carousel from 'react-bootstrap/Carousel';
import Slide1 from '../../../assets/slide1.png';
import Slide2 from '../../../assets/slide2.PNG';
import Slide3 from '../../../assets/slide3.png';


const PromoAction = () => {
    return (
    <section className={style.container_promo}>
 <Carousel data-bs-theme="dark" interval={100000}>
      <Carousel.Item>
      <div className={style.carousel_item}>
        <div className={style.carousel_img}>
        <img
          className="d-flex"
          src={Slide1}
          alt=""
        />
        </div>
        <div className={style.carousel_text}>
          <div className={style.carousel_header}>АКЦИЯ</div>
      
            <h3>Пробный урок бесплатно!</h3>
          <p>
            Первый урок мы проведём<br></br> для вас совершенно бесплатно!
          </p>
          </div>
          </div>
        <Carousel.Caption>

        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
      <div className={style.carousel_item}>
        <div className={style.carousel_img}>
        <img
          className="d-flex"
          src={Slide2}
          alt=""
        />
        </div>
        <div className={style.carousel_text}>
        <div className={style.carousel_header}>АКЦИЯ</div>
        <h3>Приведи друга!</h3>
          <p>
            Приведи друга и <br></br> получи скидку на любой курс <br></br> -30%
          </p>
          </div>
          </div>
        <Carousel.Caption className='caption'>
   
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
      <div className={style.carousel_item}>
        <div className={style.carousel_img}>
        <img
          className="d-flex"
          src={Slide3}
          alt=""
        />
        </div>
        <div className={style.carousel_text}>
        <div className={style.carousel_header}>АКЦИЯ</div>
        <h3>3+1!</h3>
          <p>
            Возьми три любых курса <br></br> и получи четвёртый <br></br> БЕСПЛАТНО!
          </p>
          </div>
          </div>
        <Carousel.Caption>
      
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
    </section>
    );
    
}

export default PromoAction;