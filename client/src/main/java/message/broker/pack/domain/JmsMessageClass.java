package message.broker.pack.domain;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JmsMessageClass implements Serializable {

    static final long serialVersionUID = -21345135432L;

    private Malfunction malfunction;
    private String message;

}
