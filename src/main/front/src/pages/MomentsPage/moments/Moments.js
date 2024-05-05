import React, { useState } from "react";
import Stories from "react-insta-stories";
import {useNavigate } from "react-router-dom";
import momentsData from "./MomentsStore/momentsData";

export default function Moments() {
  const [loading, setLoading] = useState(true);
  
  const navigate = useNavigate();
 
  function renderLoading() {
    return (
      <div className="">
        <svg class=""></svg>
      </div>
    );
  }
  function getStoriesObject() {
    const stories = momentsData.map((item) => {
   
        return {
          content: (props) => (
            <div className="">
              <div
                className=""
                style={{ backgroundImage: `url(${item.image})` }}
              >
                <div
                  className=""
                  style={{ color: item.captionColor }}
                >
                  <span>{item.caption}</span>
                </div>
              </div>
            </div>
          ),
        };
      
    });
    return stories;
  }
  return (
    <div className="">
        <Stories
          stories={getStoriesObject()}
          defaultInterval={5000}
          width={"100%"}
          height="90vh"
          onAllStoriesEnd={""}
          onStoryEnd={() => setLoading(true)}
        />
    </div>
  );
}
