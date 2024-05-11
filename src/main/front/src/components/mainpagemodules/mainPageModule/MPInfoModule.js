import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { updateInfo } from "../../redux/slices/infoMpSlice";
import { useEffect } from "react";


const MPInfoModule = (blocknum) => {

   const style = setupStyles("infoMP")
   const auth = useSelector((state) => state.auth);
   const infomp = useSelector((state) => state.infomp);
   const dispatch = useDispatch()

   useEffect(() => {
      dispatch(updateInfo(auth._id));
    }, []);



    return (
  
      <div className={style.info}>
         <div className={style.info_item}>
         {infomp.infoList.map((el, i) => {
            if (blocknum.block === 1 && el.block === 1){
               return(
                   <div key={i}>{el.name + el.infotext}</div>
             )
            }
          
      })}
{infomp.infoList.map((el, i) => {
            if (blocknum.block === 2 && el.block === 2)
              
               {
               return(
                
                   <div key={i}>{el.name + el.infotext}</div>
             
             )
            }
          
      })}

        
         </div>
      
      </div>


         );
}
 
export default MPInfoModule; 
