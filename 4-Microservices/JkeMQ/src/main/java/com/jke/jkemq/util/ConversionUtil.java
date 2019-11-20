package com.jke.jkemq.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jke.jkemq.jpa.NewAccount;
import org.springframework.messaging.Message;

import java.io.IOException;

public class ConversionUtil {
    public static String xmlToJson(String xmlString) {
        LogUtil.logDebug("ConversionUtil : xmlToJson Started ---->" + xmlString);

        String jsonString = null;
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(xmlString.getBytes());

            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(jsonNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.logDebug("ConversionUtil : xmlToJson Completed ---->" + jsonString);

        return jsonString;
    }

    public static String jsonToXml(String jsonString) {
        LogUtil.logDebug("ConversionUtil : jsonToXml Started ---->" + jsonString);

        String xmlString = null;
        try {

            ObjectMapper jsonMapper = new ObjectMapper();
            NewAccount newAccount = jsonMapper.readValue(jsonString, NewAccount.class);

            XmlMapper xmlMapper = new XmlMapper();

            xmlString = xmlMapper.writeValueAsString(newAccount);

        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.logDebug("ConversionUtil : jsonToXml Completed ---->" + xmlString);
        return xmlString;
    }

    public static Object jsonToObject(String jsonString, Class className) {
        Object result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            result = objectMapper.readValue(jsonString, NewAccount.class);
        } catch (IOException e) {
            LogUtil.logDebug("ConversionUtil : jsonToObject Error ---->" + e.getMessage());
            e.printStackTrace();

        }
        return result;
    }

    public static String jmsMessageInXmlToJson(final Message xmlMessage) {
        LogUtil.log("ConversionUtil : jmsMessageInXmlToJson: started  ");
        String jsonString = null;
        try {
            String xmlString = xmlMessage.getPayload().toString();
            LogUtil.log("ConversionUtil : jmsMessageInXmlToJson: Received message in XML --->>> " + xmlString);

            //converting xml to json
            jsonString = ConversionUtil.xmlToJson(xmlString);
            LogUtil.log("ConversionUtil : jmsMessageInXmlToJson: Received message in JSON --->>> " + jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.log("ConversionUtil : jmsMessageInXmlToJson: completed ---> " + jsonString);
        return jsonString;
    }

    public static String jmsMessageInJsonToXml(final Message xmlMessage) {
        LogUtil.log("ConversionUtil : jmsMessageInJsonToXml: started  ");
        String xmlString = null;
        try {
            String jsonString = xmlMessage.getPayload().toString();
            LogUtil.log("ConversionUtil : jmsMessageInJsonToXml: Received message in JSON --->>> " + jsonString);

            //converting json to xml
            xmlString = ConversionUtil.jsonToXml(jsonString);
            LogUtil.log("ConversionUtil : jmsMessageInJsonToXml: Received message in XML --->>> " + xmlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.log("ConversionUtil : jmsMessageInJsonToXml: completed ---> " + xmlString);
        return xmlString;
    }

    public static String jmsMessageInJson(final Message xmlMessage) {
        LogUtil.log("ConversionUtil : jmsMessageInJson: started  ");
        String jsonString = null;
        try {
            jsonString = xmlMessage.getPayload().toString();
            LogUtil.log("ConversionUtil : jmsMessageInJson: Received message in JSON --->>> " + jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.log("ConversionUtil : jmsMessageInJson: completed ---> " + jsonString);
        return jsonString;
    }

}
