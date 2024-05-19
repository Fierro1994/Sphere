import { useDispatch, useSelector } from "react-redux";
import setupStyles from "../../../pages/stylesModules/setupStyles";
import { updateInfo, uploadInfo } from "../../redux/slices/infoMpSlice";
import { createRef, useEffect, useState } from "react";
import { MdOutlineKeyboardArrowDown } from "react-icons/md";

const MPInfoModule = (blocknum) => {
   const style = setupStyles("infoMP")
   const auth = useSelector((state) => state.auth);
   const infomp = useSelector((state) => state.infomp);
   const [showPopup, setShowPopup] = useState(false)
   const dispatch = useDispatch()
   var listI = []
   useEffect(() => {
      dispatch(updateInfo(auth._id));
   }, []);

   const onSubmitForm1 = (e) => {
      e.preventDefault()
      for(var i = 0; i <3; i++){
          listI.push({ name: e.target[i].value,
            block: e.target[i].name
          })
      }
      
      
      const myData = new FormData();
 
         myData.append('infoModules', JSON.stringify(listI))
         myData.append('userId', auth._id);
         dispatch(uploadInfo(myData))
   }

   useEffect(() => {
      if (infomp.isUpdFul) {
        
         setShowPopup(false)
      }
   }, [infomp]);
   return (

      <div className={style.info}>
         {showPopup && <div className={style.popup_cont}>
         {(blocknum.block === 1) &&<form onSubmit={onSubmitForm1} >
               <p>Введите значения в нужные поля!</p>
             
                  {infomp.infoList.map((el, i) => {
                     if(blocknum.block === 1  && el.block <= 3){
                           return (
                              <div key={i} className={style.inp_block}>
                              <label >
                                 <input type="text" onChange={(e) => {
                                    
                                 }}
                                    name={el.block} defaultValue={el.name === null ? "" :el.name } maxLength="40" />
                              </label >
                              </div>
                           )
                        
                     }
                    
                  })}
              <input className={style.submit_btn}  type="submit"  value={"Сохранить"}/>
               
            </form>}

            {(blocknum.block === 2) &&<form onSubmit={onSubmitForm1} >
               <p>Введите значения в нужные поля!</p>
             
                  {infomp.infoList.map((el, i) => {
                     if(blocknum.block === 2 && el.block > 3){
                        return (
                           <div key={i} className={style.inp_block}>
                           <label >
                              <input type="text" onChange={(e) => {
                                 
                              }}
                                 name={el.block} defaultValue={el.name === null ? "" :el.name } maxLength="40" />
                           </label >
                           </div>
                        )
                     }
                    
                  })}
              
              <input className={style.submit_btn}  type="submit"  value={"Сохранить"}/>
            </form>}
         </div>}
         <span className={style.cntrl_btn}
            onClick={(e) => {
               setShowPopup(!showPopup)
            }}>


            <MdOutlineKeyboardArrowDown />
         </span>
         {blocknum.block === 1 && <div className={style.info_item}>
            
               {infomp.infoList.map((el, i) => {
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
            
            {infomp.infoList.map((el, i) => {
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

export default MPInfoModule; 
