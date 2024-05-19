package com.example.Sphere.models.request;


import com.example.Sphere.entity.InfoModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoUploadReq {
private String userId;
private List<InfoModule> infoModules;

}
