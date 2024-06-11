import {useSelector } from "react-redux";
import setupStyles from "../../../pages/stylesModules/setupStyles";

const UPInfoModule = (blocknum) => {
    const style = setupStyles("infoMP")
    const userPage = useSelector((state) => state.userspages);
    return (
        <div className={style.info}>
            {blocknum.block === 1 && <div className={style.info_item}>
                {userPage.infoList.map((el, i) => {
                    if (blocknum.block === 1) {
                        if (el.block == 1) {
                            return (
                                <div className={style.block_div} key={i}>{el.name}</div>
                            )
                        }
                        if (el.block == 2) {
                            return (
                                <div className={style.block_div} key={i}>{el.name}</div>
                            )
                        }
                        if (el.block == 3) {
                            return (
                                <div className={style.block_div} key={i}>{el.name}</div>
                            )
                        }
                    }
                })}
            </div>}
            {blocknum.block === 2 && <div className={style.info_item}>
                {userPage.infoList.map((el, i) => {
                    if (blocknum.block === 2) {
                        if (el.block == 4) {
                            return (
                                <div className={style.block_div} key={i}>{el.name}</div>
                            )
                        }
                        if (el.block == 5) {
                            return (
                                <div className={style.block_div} key={i}>{el.name}</div>
                            )
                        }
                        if (el.block == 6) {
                            return (
                                <div className={style.block_div} key={i}>{el.name}</div>
                            )
                        }
                    }
                })}
            </div>}
        </div>
    );
}

export default UPInfoModule; 
