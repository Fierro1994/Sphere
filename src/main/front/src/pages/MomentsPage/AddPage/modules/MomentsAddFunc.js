import { useEffect, useRef, useState } from "react";
import setupStyles from "../../../stylesModules/setupStyles";
function MomentsAddFunc() {
    const style2 = setupStyles("mainstyle")
    const style = setupStyles("momentsrecordstyle")
    const [isEnabled, setEnabled] = useState(false);
    const [error, setError] = useState();
    const [facing, setFacing] = useState("user");
    const canvasRef = useRef(null);
    const videoRef = useRef(null);
    const streamRef = useRef(null);
    const videoType = 'video/webm';

    // document.addEventListener('visibilitychange', setEnabled(false));
    const enabledStream = () => {
      navigator.mediaDevices
        .getUserMedia({
          audio: true,
          video: {
            facingMode: { exact: facing },
          },
        })
        .then((stream) => {
          streamRef.current = stream;
          videoRef.current.srcObject = streamRef.current;
          videoRef.current.onloadedmetadata = () => videoRef.current.play();
          streamRef.current.mediaRecorder = new MediaRecorder(stream, {
                  mimeType: videoType
                });
                streamRef.chunks = [];
             streamRef.current.mediaRecorder.ondataavailable = e => {
                          if (e.data && e.data.size > 0) {
                            streamRef.chunks.push(e.data);
                          }
                        };
        })
        .catch((err) => {
          setError(err.name);
        });
    };
  
    const disableStream = () => {
      if (streamRef.current) {
        streamRef.current.getTracks().forEach((track) => track.stop());
      }
    };

    // const startRecording = async () => {
    //         // wipe old data chunks
    //         streamRef.chunks = [];
    //         // start recorder with 10ms buffer
    //         streamRef.current.mediaRecorder.start(10);
    //         console.log(streamRef.chunks);
    //         // say that we're recording
    //         await this.setState({ recording: true, canSend: false });
    //       }

    const stopRecording = async (e) => {
                e.preventDefault();
                // stop the recorder
                this.mediaRecorder.stop();
                // say that we're not recording
                await this.setState({ recording: false, canSend: true });
                // save the video to memory
                this.saveVideo();
              }
  
    const makePhoto = () => {
      const videoWidth = videoRef.current.scrollWidth;
      const videoHeight = videoRef.current.scrollHeight;
      canvasRef.current.width = videoWidth;
      canvasRef.current.height = videoHeight;
      if (facing === "user") {
        const context = canvasRef.current.getContext("2d");
        context.scale(-1, 1);
        context.drawImage(videoRef.current, 0, 0, -videoWidth, videoHeight);
      } else {
        canvasRef.current
          .getContext("2d")
          .drawImage(videoRef.current, 0, 0, videoWidth, videoHeight);
      }
    };
  
    const deletePhoto = () => {
      console.log("delete");
      const context = canvasRef.current.getContext("2d");
      context.clearRect(0, 0, canvasRef.current.width, canvasRef.current.height);
      context.clearRect(0, 0, -canvasRef.current.width, canvasRef.current.height);
    };
  
    const downloadPhoto = () => {
      const link = document.createElement("a");
      link.download = "photo.png";
      link.href = canvasRef.current.toDataURL("image/png");
      link.click();
    };
  
    useEffect(() => {
      setError(null);
      disableStream();
    //   startRecording()
      if (isEnabled) enabledStream();
    }, [isEnabled, facing]);
  

    return (<div>
        <div className={style2.container}>
       
        <div className={style2.container2}>
        <div className={style2.menu_items}>
      <div className={style2.circle}>
        
        </div>
      </div>
              <video
        id={style.video}
          className={facing === "user" ? style.mirror : ""}
          playsInline
          muted
          autoPlay
          ref={videoRef}
        ></video>
        
      <div>
      
        <canvas id={style.canvas} ref={canvasRef}></canvas>
        {error && <div className={style.error}>{error}</div>}
        {isEnabled && <h1 id={style.h1}>{facing === "user" ? "Front Cam" : "Back Cam"}</h1>}
        <div className={style.controls}>
        </div>
      </div>
  
        </div>
       
       </div>

     </div>
     
    );
  }
  
  export default MomentsAddFunc;


// /* eslint-env browser */
// // import React from 'react';
// // import { Player, ControlBar, ForwardControl } from 'video-react';
// // 

// // const VideoRecorder = () => {

// //   initiateRecording = async () => {
// //     const stream = await navigator.mediaDevices.getUserMedia({
// //       video: true,
// //       audio: true
// //     });
// //     // show it to user
// //     this.video.srcObject = stream;
// //     this.video.play();
// //     // init recording
// //     this.mediaRecorder = new MediaRecorder(stream/*, {
// //       mimeType: videoType
// //     }*/);
// //     // init data storage for video chunks
// //     this.chunks = [];
// //     // listen for data from media recorder
// //     this.mediaRecorder.ondataavailable = e => {
// //       if (e.data && e.data.size > 0) {
// //         this.chunks.push(e.data);
// //       }
// //     };
// //   }

// //   startRecording = async (e) => {
// //     e.preventDefault();
// //     // wipe old data chunks
// //     this.chunks = [];
// //     // start recorder with 10ms buffer
// //     this.mediaRecorder.start(10);
// //     // say that we're recording
// //     await this.setState({ recording: true, canSend: false });
// //   }

// //   stopRecording = async (e) => {
// //     e.preventDefault();
// //     // stop the recorder
// //     this.mediaRecorder.stop();
// //     // say that we're not recording
// //     await this.setState({ recording: false, canSend: true });
// //     // save the video to memory
// //     this.saveVideo();
// //   }

// //   restartRecording = () => {
// //     this.initiateRecording();
// //     this.setState({ recording: false, videoURL: null, canSend: false });
// //   }

// //   saveVideo = () => {
// //     // convert saved chunks to blob
// //     const blob = new Blob(this.chunks, {type: videoType});
// //     // generate video url from blob
// //     const videoURL = window.URL.createObjectURL(blob);
// //     // append videoURL to list of saved videos for rendering
// //     //const videos = this.state.videos.concat([videoURL]);
// //     this.setState({ video: blob });
// //     this.setState({ videoURL });
// //     //console.log(videoURL, 'vidURL');
// //     /*  convert blob to base64
// //     var xhr = new XMLHttpRequest();
// //     xhr.responseType = 'blob';

// //     xhr.onload = () => {
// //       var recoveredBlob = xhr.response;

// //       var reader = new FileReader();

// //       reader.onload = () => {
// //         var blobAsDataUrl = reader.result;
// //         this.setState({ videoURL: blobAsDataUrl });
// //         console.log(blobAsDataUrl.type, 'bloburi');
// //         console.log(this.state.videoURL);
// //       };

// //       reader.readAsDataURL(recoveredBlob);
// //     };

// //     xhr.open('GET', videoURL);
// //     xhr.send();
// //     */
// //   }
  
// //   videoDisplay = () => {
// //     if (this.state.videoURL != null) {
// //       return (
    
// //           <Player>
// //             <source src={this.state.videoURL} type="video/mp4" />
// //             <ControlBar autoHide={true}>
// //               <ForwardControl seconds={5} order={3.1} />
// //             </ControlBar>
// //           </Player>
// //       )
// //     } 
// //     else if (this.state.videoURL == null) {
// //       return(
       
// //           <video
// //             style={{width: '100%', height: '400'}}
// //             ref={v => {
// //               this.video = v;
// //             }}
// //             muted
// //           >
// //             Video stream not available.
// //           </video>
       
// //       )
// //     }
// //   }

// //   displayButton = () => {
// //     if (this.state.canSend && (this.state.videoURL != null)) {
// //       return (
// //         <div>
// //           <button size="large" color="primary"
// //             onClick={() => {
// //               this.restartRecording();
// //             }}
// //           >
// //             Restart Recording
// //           </button>
// //           <button size="large" color="primary"
// //             onClick={() => {
// //               this.submitVideo(this.state.video);
// //             }}
// //           >
// //             Submit
// //           </button>
// //           <a className="MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeLarge" tabIndex="0" type="button" href={this.state.videoURL} download={Math.random+".mp4"}>
// //             <span className="MuiButton-label">Download</span>
// //             <span className="MuiTouchRipple-root"></span>
// //           </a>
// //         </div>
// //       );
// //     } 
// //     else if (this.state.videoURL == null) {
// //       return (
// //         (this.state.recording === false) ? 
// //           <button size="large" color="primary" onClick={e => this.startRecording(e)}>
// //             Start Recording
// //           </button> : 
// //           <button size="large" color="primary" onClick={e => this.stopRecording(e)}>
// //             Stop Recording
// //           </button>
// //       )
// //     }
// //   }

// //   submitVideo = (video) => {
// //     //console.log(video, 'video');
// //     var url = window.location.href+'single';
// //     const formData = new FormData();
// //     formData.append('profile', video, Math.random + '.mp4');

// //     fetch(url, {
// //       method: 'POST', // or 'PUT'
// //       mode: 'no-cors',
// //       body: formData
// //     }).then(res => {
// //       this.setState({ 
// //         visible: true, 
// //         alertColor: 'success', 
// //         alertMessage: 'CONGRATS! Your video has been uploaded.',
// //         canSend: false,
// //         videoURL: null
// //       })
// //       this.initiateRecording();
// //     }).catch(err => {
// //       this.setState({ 
// //         visible: true, 
// //         alertColor: 'danger', 
// //         alertMessage: 'OOPS! Your video was not uploaded.',
// //         canSend: true,
// //         videoURL: this.state.videoURL
// //       })
// //       return (

// //           <Player>
// //             <source src={this.state.videoURL} />
// //             <ControlBar autoHide={true}>
// //               <ForwardControl seconds={5} order={3.1} />
// //             </ControlBar>
// //           </Player>
// //       )
// //     });
// //   }

// //   onDismiss = () => {
// //     this.setState({ 
// //       visible: false
// //     });
// //   }

  
// //     //const { recording, videoURL } = this.state;
  

// //     return (
// //       <div className='row'>
// //         <div className='mt-5 col-lg-6 offset-lg-3'>
// //             {this.videoDisplay()}
           
// //               {this.displayButton()}
           
            
               
           
          
// //           {/*console.log(videoURL)*/}
// //         </div>
// //       </div>
// //     );

// // }

// // export default VideoRecorder


