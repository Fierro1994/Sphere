import setupStyles from "../../../stylesModules/setupStyles";
import {useState} from "react";


const MainPageSetNav = () => {
   const style = setupStyles("mainpagesetstyle")
    const [showDropdown, setShowDropdown] = useState(false);
    const handleMenuClick = () => {
        setShowDropdown(!showDropdown);
    };
    return (
        <>
            <div className={style.nav}>
                <p>Навигация</p>
                <button className={style.plus} onClick={handleMenuClick}>+</button>
                {showDropdown && (
                    <div className={style.dropdownContent}>
                        {/* Добавьте содержимое выпадающего списка здесь */}
                        <a href="#">Ссылка 1</a>
                        <a href="#">Ссылка 2</a>
                        <a href="#">Ссылка 3</a>
                    </div>
                )}
            </div>
        </>
         );
}
 
export default MainPageSetNav; 
