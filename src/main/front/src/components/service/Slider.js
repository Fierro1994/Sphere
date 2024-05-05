import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { instanceWidthCred } from "../auth/api/api";
 const Slider = () => {
  const [importedComponent, setImportedComponent] = useState(null);
    const [ImagesList, setImageList] = useState([])
    var mainPageModules = []
  if (localStorage.getItem("mainPageModules")) {
    mainPageModules = JSON.parse(localStorage.getItem("mainPageModules"))
  }
  async function onSubmit(params) {
    await instanceWidthCred.post("/imagepromo", {
    
    });
    
  } 
  const [result,setResult] = useState(null)
  useEffect(() => {
   
    mainPageModules.map((element, i) => {
      if (element.name === "PROMO") {
        setResult( element.pathImage.map((element2, i) => {
          // const importComponent = async () => {
          //   const module = await import(element2);
          //   console.log("../../../" + AnotherComponent);
          //   const AnotherComponent = module.default;
        
          //   setImportedComponent(AnotherComponent);
          // };
      
          // importComponent();
         
          return <div key={i}><img src={"../../../" + importedComponent}></img></div>
        }));
      }
    })

   
  }, []);

  
    

    return (

      <>
      {result}
      <form onSubmit={onSubmit}>

        </form>
     </>

    )
  
   

  }
  export default Slider;