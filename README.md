# How to Use AWS SQS With Sprint Boot

### First, let’s see some benefits of SQS.

1. We do not have to manage SQS. AWS will do all the management tasks.
2. Super-fast message delivery. But we can also configure delay if necessary.
3. DLQ (Dead Letter Queue) support. I will discuss DLQ shortly.
4. SQS will scale automatically based on the number of messages. There is no limitation of messages per queue.
5. SQS is cost-effective. No capacity planning is necessary. Calculations are based on usage.

### Use Cases

1. Decoupling microservices: In a big architecture, we may have many microservices. Communication between microservices
   is really complex. It would be great if we could decouple the microservices. So, they do not have to depend on each
   other. SQS can be used for this case. One service can publish the data to a queue, and other services can pull that
   data and process them. It is super-fast so that it will feel like real-time. Also, a long chain of processing can be
   simplified using SQS. With proper state management, we will know which state is causing the failure. For example, a
   restaurant management system can receive the order from customers. The order can be pushed to an SQS queue, and the
   processing service can pick it up. After preparing the order, the processing service can push the order id to the
   serving queue. The serving queue then shows that order id in the display. No service has to depend on any other
   service.
2. Trigger background (async) event: Not all events require real-time response. Some might trigger background events
   too. Like if users buy anything online, that might trigger an event to update the inventory. Updating inventory may
   take time, and users don't need to wait until the inventory is updated. Instead, we can push it to SQS and let other
   services handle the remaining processing. This is just one background example. There can be thousands.

### Queue Types

1. Standard Queue This is super-fast, unlimited TPS, guaranteed at-least-once delivery, but not ordering.
2. FIFO Queue:
   It can process 300 messages per second. But it can be increased. It guarantees exactly-once processing. And also
   maintains orders.

### Terminology

1. Payload: Payload is the message content. The message size can be a maximum of 256KB. AWS will bill us for each 64KB.
   So, a 256KB payload will be billed 4 times.
2. Long polling: Enabling Long polling will reduce SQS costs. If there is no message in the queue, SQS will wait for a
   maximum of 20s until a message arrives. This will allow reducing empty responses.
3. Dead Letter Queues (DLQ): If our queue could not process any request, it will push the messages to DLQ if configured.
   DLQ is just another SQS queue. It is helpful to have a DLQ set up to easily investigate why we never receive some
   requests or why the requests failed.

### Integrating AWS SQS with Spring Boot

First, I will create a queue in SQS. Initially, I will select queue type. This type cannot be changed after creation. I
am selecting the standard type. The queue name is Order.

Visibility timeout: This is when the message will be hidden from other consumers if the client does not delete the
message.

Delivery delay: This is when the message will be hidden from consumers after the message is pushed to the queue.

Receive message wait time: This is for long polling.

Message retention period: Message will be deleted automatically after a time.

Maximum message size: Max size of the payload.

After creating the queue, I will add maven dependencies in pom.xml
