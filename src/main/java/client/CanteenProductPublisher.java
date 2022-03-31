package client;

import org.apache.commons.lang3.SerializationUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import shared.Meniu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class CanteenProductPublisher {
    private final static String QUEUE_NAME = "via_canteen_products_queue";

    public static void main(String[] args) throws IOException, TimeoutException {


        List<Meniu> meniuList = new ArrayList<>();
        meniuList.add(new Meniu(0,"Cappuccino Cafe Latte",2));
        meniuList.add(new Meniu(1,"Cappuccino Cafe Latte",1));
        meniuList.add(new Meniu(2,"Deep fried Hawai Pizza",10));
        meniuList.add(new Meniu(3,"Deep fried Hawai Pizza",100));

        //factory pattern to init connection
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // create the connection using the factory instance
        // and initialize the channel using connection
        // channel is responsible for sending, receiving, plus some queue operations
        try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel()) {
            // with channel, we can declare, bind, unbind, del queue, ...
            // declare the queue
            // queue name, passive?, exclusive?, autoDel? any arguments
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // publish message directly to the provided queue

                for (var item : meniuList) {
                    byte[] data= SerializationUtils.serialize(item);
                    // exchange, routingKey, AMQP.properties, byte[] body of msg
                    channel.basicPublish("", QUEUE_NAME, null, data);
                    System.out.println(" [Pub] Sent '" + item + "'" + " in byte :" + data);
                }


        }
    }
}
