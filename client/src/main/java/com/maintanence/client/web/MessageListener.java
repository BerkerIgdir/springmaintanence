package com.maintanence.client.web;

import com.maintanence.client.config.JmsConfig;
import message.broker.pack.domain.JmsMessageClass;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class MessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload JmsMessageClass messageClass,
                       @Headers MessageHeaders headers,
                       Message message){
        System.out.println("Got a Message");

        System.out.println(messageClass);

    }

}
