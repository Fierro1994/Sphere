import UPInfoModule from '../modules/UPInfoModule.js';
import UPPromoModule from '../modules/UPPromoModule.js';
import UPHeaderModule from '../modules/UPHeaderModule.js';
import UPActualModule from '../modules/UPActualModule.js';
import UPNavModule from '../modules/UPNavModule.js';
import UPContentModule from '../modules/UPContentModule.js';

function UsersPageModuleChanger(listModule) {
    let listMenuModules = listModule
    const listModuleName = []
    var infoModule = []

    listMenuModules.map(element => {
        if (element.isEnabled && element.name === "INFO") {
            infoModule.push(<UPInfoModule/>)
        }
    })

    listMenuModules.map(element => {
        if (element.isEnabled && element.name === "PROMO") {
            listModuleName.splice(0, 0, <UPPromoModule />)
        }
      
        if (element.isEnabled && element.name === "HEADER") {
            if(infoModule === null){
                listModuleName.push(<UPHeaderModule />)
            }else{
                listModuleName.push(<UPHeaderModule info = {infoModule}/>)
            }     
        }
        if (element.isEnabled && element.name === "ACTUAL") {
            listModuleName.splice(1,0,<UPActualModule />)
        }
        if (element.isEnabled && element.name === "NAVIGATION") {
            listModuleName.splice(2, 0, <UPNavModule />)
        }
        if (element.isEnabled && element.name === "CONTENT") {
            listModuleName.splice(3, 0, <UPContentModule />)
        }
        
       
    });
    return listModuleName
}


export default UsersPageModuleChanger;