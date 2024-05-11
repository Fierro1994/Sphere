import setupStyles from "../../../pages/stylesModules/setupStyles";


const Comment_avatar = () => {
  
    const style = setupStyles("headerMP")




  return (
    <>
   
<div className={style.comment_cont}>
<div className={style.comment_main}>Комментарии</div>
</div>
      
    </>
  )
}
export default Comment_avatar;