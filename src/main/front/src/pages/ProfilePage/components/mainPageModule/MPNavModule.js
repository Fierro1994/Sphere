import setupStyles from "../../../stylesModules/setupStyles";


const MPNavModule = () => {
    const style = setupStyles("MPNavStyle")
    const handleMenuClick = () => {
    };
    return (
        <>
            <div className={style.nav_menu}>
                    <button className={style.btn_nav}>Ссылка 1</button>
                    <button  className={style.btn_nav}>Ссылка 2</button>
                    <button  className={style.btn_nav}>Ссылка 3</button>
                    <button className={style.plus} onClick={handleMenuClick}>+</button>
                </div>
        </>
    );
}

export default MPNavModule;
