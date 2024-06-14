import React, { useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {loadImageBlob} from "../../../../components/redux/slices/promoSlice";

const ImageView = ({ imageName, nextImageName }) => {
    const dispatch = useDispatch();
    const imageRef = useRef();
    const imageUrl = useSelector((state) => state.promo.images[imageName]);
    const nextImageUrl = useSelector((state) => state.promo.images[nextImageName]);
    const error = useSelector((state) => state.promo.error);
    const observer = useRef();

    console.log(imageUrl)

    useEffect(() => {
        observer.current = new IntersectionObserver(
            (entries) => {
                if (entries[0].isIntersecting) {
                    dispatch(loadImageBlob(imageName));
                    if (nextImageName && !nextImageUrl) {
                        dispatch(loadImageBlob(nextImageName));
                    }
                    observer.current.disconnect();
                }
            },
            { threshold: 0.1 }
        );

        if (imageRef.current) {
            observer.current.observe(imageRef.current);
        }

        return () => {
            if (observer.current && imageRef.current) {
                observer.current.unobserve(imageRef.current);
            }
        };
    }, [dispatch, imageName, nextImageName, nextImageUrl]);

    return (
        <div ref={imageRef} style={{ minHeight: '200px', minWidth: '200px' }}>
            {error && <p>Error: {error}</p>}
            {imageUrl ? <img src={imageUrl} alt={imageName} /> : <p>Loading...</p>}
        </div>
    );
};

export default ImageView;