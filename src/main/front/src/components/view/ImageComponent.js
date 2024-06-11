import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { instanceWidthCred } from '../auth/api/instance';

function ImageComponent({ imageNames }) {
    const [imageSrcs, setImageSrcs] = useState([]);

    useEffect(() => {
        // Функция для получения изображений по именам
        const fetchImages = async () => {
            try {
                // Делаем HTTP-запросы для получения изображений с сервера
                const requests = imageNames.map(imageName =>
                    instanceWidthCred.get(`/avatar/listavatars`, { responseType: 'arraybuffer' })
                );
                const responses = await Promise.all(requests);

                // Обрабатываем каждый ответ и формируем массив base64-строк
                const imageSrcs = await Promise.all(responses.map(async response => {
                    const base64 = btoa(
                        new Uint8Array(response.data)
                            .reduce((data, byte) => data + String.fromCharCode(byte), '')
                    );
                    return `base64,${base64}`;
                }));

                setImageSrcs(imageSrcs);
            } catch (error) {
                console.error('Error fetching images:', error);
            }
        };

        fetchImages();
    }, [imageNames]);

    return (
        <div>
            {imageSrcs.map((src, index) => (
                <img key={index} src={src} alt={`Image ${index}`} />
            ))}
        </div>
    );
}

export default ImageComponent;