package com.maintanence.client.web;

import com.maintanence.client.config.JmsConfig;
import message.broker.pack.domain.JmsMessageClass;
import message.broker.pack.domain.Malfunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
@EnableAsync
@RequiredArgsConstructor
public class MalfunctionProducer {

    private String[] malfunctionTypes = {"MECHANICAL","ELECTRICAL"};

    private Random rand = new Random();

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 1000)
    @Async
    public void createMalfunctions(){

    log.info("Request is being sent");

      Malfunction malfunctionMono =  Malfunction.builder()
              .machineId(UUID.randomUUID())
              .description("RANDOM GENERATED")
              .malfunctionType(malfunctionTypes[rand.nextInt(2)])
              .build();

        JmsMessageClass message = JmsMessageClass.builder()
                .malfunction(malfunctionMono)
                .message(" An inter-services message")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,message);

    }



}
