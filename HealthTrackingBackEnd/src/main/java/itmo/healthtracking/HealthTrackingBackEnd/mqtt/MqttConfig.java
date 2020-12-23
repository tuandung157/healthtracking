package itmo.healthtracking.HealthTrackingBackEnd.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.healthtracking.HealthTrackingBackEnd.model.Device;
import itmo.healthtracking.HealthTrackingBackEnd.model.Telemetry;
import itmo.healthtracking.HealthTrackingBackEnd.repository.DeviceRepository;
import itmo.healthtracking.HealthTrackingBackEnd.repository.TelemetryRepository;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import java.util.Date;


@Configuration
public class MqttConfig {

    @Autowired
    DeviceRepository dr;
    @Autowired
    TelemetryRepository tr;

    @Bean
    public DefaultMqttPahoClientFactory clientFactory(){
        var fact = new DefaultMqttPahoClientFactory();
        MqttConnectOptions ops = new MqttConnectOptions();
        ops.setUserName("spring");
        ops.setPassword("oracle1".toCharArray());
        fact.setConnectionOptions(ops);
        return fact;
    }

    @Bean
    public IntegrationFlow mqttInbound() {
        var adapter = new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883",
                "springBack", clientFactory(), "telemetry");

        return IntegrationFlows.from(adapter)
                .handle(m -> {
                    Telemetry t;
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        t = mapper.readValue(m.getPayload().toString(), Telemetry.class);
                        Device d = dr.findByClientName(t.getDevice().getClientName());
                        if (d != null){
                            t.setDevice(d);
                        }
                        else {
                            t.getDevice().setCreatedAt(new Date());
                        }
                        t.getDevice().setUpdatedAt(new Date());
                        dr.save(t.getDevice());
                        t.setCreatedAt(new Date());
                        t.setUpdatedAt(new Date());
                        tr.save(t);
                    } catch (JsonProcessingException e){
                        System.out.println("Couldn't get telemetry value out of message payload");
                        e.printStackTrace();
                    }
                    // insert data to database here
                    System.out.println(m.getPayload());
                })
                .get();
    }
}
