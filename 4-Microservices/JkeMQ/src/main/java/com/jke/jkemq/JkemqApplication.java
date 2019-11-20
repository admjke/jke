package com.jke.jkemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class JkemqApplication {

	public static void main(String[] args) {
        SpringApplication.run(JkemqApplication.class, args);
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.


        // anonymous class
        factory.setErrorHandler(
                new ErrorHandler() {
                    @Override
                    public void handleError(Throwable t) {
                        System.err.println("An error has occurred in the transaction 1111---------> **** --------->" + t.toString());
                        t.printStackTrace();
                    }
                });

        // lambda function
        factory.setErrorHandler(t -> {
            System.err.println("An error has occurred in the transaction 2222 ---------> **** --------->" + t.toString());
            t.printStackTrace();
        });

        factory.setMessageConverter(new MappingJackson2MessageConverter());

        configurer.configure(factory, connectionFactory);

        return factory;
    }
//
//    @Bean // Serialize message content to json using TextMessage
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }

//    public void stopLister() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JkemqApplication.class);
//        JmsListenerEndpointRegistry bean = context.getBean(JmsListenerEndpointRegistry.class);
//        for (MessageListenerContainer listenerContainer : bean.getListenerContainers()) {
//            listenerContainer.stop();
//        }
//    }
}
