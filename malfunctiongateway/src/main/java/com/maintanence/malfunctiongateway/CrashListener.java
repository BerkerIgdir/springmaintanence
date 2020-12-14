package com.maintanence.malfunctiongateway;

import com.maintanence.malfunctiongateway.config.JmsConfig;
import message.broker.pack.domain.JmsMessageClass;
import message.broker.pack.domain.Malfunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Slf4j
@Component
public class CrashListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
   public void listener(@Payload JmsMessageClass message, @Headers MessageHeaders messageHeaders, Message theMessage){

        Malfunction malfunction = message.getMalfunction();

        log.info(message.getMessage());

    }

}
