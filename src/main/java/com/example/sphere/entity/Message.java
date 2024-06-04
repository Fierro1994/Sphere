
package com.example.sphere.entity;

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
