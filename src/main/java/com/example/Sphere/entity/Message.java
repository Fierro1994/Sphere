
package com.example.Sphere.entity;

import com.example.Sphere.entity.EMessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private EMessageType type;
    private String content;
    private String sender;
    private String sessionId;
}
