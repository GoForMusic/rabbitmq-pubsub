package client2;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import shared.Meniu;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CanteenProductSubscriber {
    private final static String QUEUE_NAME = "via_canteen_products_queue";
    public static void main(String[] args) throws IOException, TimeoutException
    {
        // factory patter for the connection
        ConnectionFactory factory = new ConnectionFactory();
        // set the host name
        factory.setHost("localhost");
        // create connection and initialze the channel
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // use the default consumer to control the deliveries
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                                 Envelope envelope,
                                                 AMQP.BasicProperties properties, byte[] body)
                    throws IOException
            {
                //super.handleDelivery(consumerTag, envelope, properties,body);
                Meniu object = SerializationUtils.deserialize(body);
                System.out.println(" [Sub] Received '" + object + "'");
            }
        };
        // consume using: queue, bool noAck, callback
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}
