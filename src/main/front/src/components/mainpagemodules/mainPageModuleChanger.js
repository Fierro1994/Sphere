import MPActualModule from './mainPageModule/MPActualModule.js';
import MPHeaderModule from './mainPageModule/MPHeaderModule.js';
import MPInfoModule from './mainPageModule/MPInfoModule.js';
import MPPromoModule from './mainPageModule/MPPromoModule.js';
import MPNavModule from './mainPageModule/MPNavModule.js';
import MPContentModule from './mainPageModule/MPContentModule.js';

function mainPageModuleChanger(listModule) {
    let listMenuModules = listModule
    const listModuleName = []
    var infoModule = []

    listMenuModules.map(element => {
        if (element.isEnabled && element.name === "INFO") {
            infoModule.push(<MPInfoModule/>)
        }
    })

    listMenuModules.map(element => {
        if (element.isEnabled && element.name === "PROMO") {
            listModuleName.splice(0, 0, <MPPromoModule />)
        }
      
        if (element.isEnabled && element.name === "HEADER") {
            if(infoModule === null){
                listModuleName.push(<MPHeaderModule />)
            }else{
                listModuleName.push(<MPHeaderModule info = {infoModule}/>)
            }     
        }
        if (element.isEnabled && element.name === "ACTUAL") {
            listModuleName.splice(1,0,<MPActualModule />)
        }
        if (element.isEnabled && element.name === "NAVIGATION") {
            listModuleName.splice(2, 0, <MPNavModule />)
        }
        if (element.isEnabled && element.name === "CONTENT") {
            listModuleName.splice(3, 0, <MPContentModule />)
        }
        
       
    });
    return listModuleName
}


export default mainPageModuleChanger;