package message.broker.pack.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JmsMessageClass implements Serializable {

    static final long serialVersionUID = -213451351231541432L;

    private Malfunction malfunction;
    private String message;

}
