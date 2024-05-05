
import React, { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {deleteGaleryFile, updateGallery } from '../../../components/redux/slices/galerySlice';
import setupStyles from '../../stylesModules/setupStyles';

export function readFile(file) {
    return new Promise((resolve) => {
      const reader = new FileReader()
      reader.addEventListener('load', () => resolve(reader.result), false)
      reader.readAsDataURL(file)
    })
  }

function GaleryFileManager() {
    const PATH = "http://localhost:3000/gallery"
    const dispatch = useDispatch()
    const auth = useSelector((state) => state.auth);
    const gallery = useSelector((state) => state.gallery);
    const [allData, getAllFile] = useState([]);
   
    const style = setupStyles("galerypagestyle")
   
    const deleteFile = (key) => {
        key.preventDefault()
        const formData = new FormData();
        formData.append("id", auth._id)
        formData.append("key", key.target.value)
        dispatch(deleteGaleryFile(formData))
    }

    function result(el) {
        return PATH+ "/" + auth._id + "/" + el
    }
    useEffect(() => {
        dispatch(updateGallery(auth._id))
       }, [dispatch]);

    useEffect(() => {
       getAllFile(gallery.galleryList)
      }, [gallery.galleryList]);
    return (
        <>
       
        <div className={style.gallery_contain}>
        {gallery.galleryList && allData.map((element, l) => (<div className={style.carts_image} key={l}><img   src={result(element)}></img></div>)
                )}
        </div>
       
        </>
                
       
    );
}

export default GaleryFileManager;