### Spring AMQP RPC Server
- 1：消息处理方法，一定要有返回值，这个返回值就是server回复客户端的结果

- 2：server端返回的结果，一定要注意。和MessageConverter有关，默认的Simple MessageConverter，会把基本数据类型转换成Serializable对象，这样的话，client端接收的也是序列化的java对象。所以，需要合理设置MessageConverter

- 3.Spring AMQP RPC Server有两种处理方案：<br>
  1).使用SimpleMessageListenerContainer来监听队列<br>
  2).使用@RabbitListener注解来监听队列

### Spring AMQP RPC Client
- 1：使用sendAndReceive方法发送消息，该方法返回一个Message对象，该对象就是server返回的结果
- 2： sendAndReceive如果超过5秒还没收到结果，则返回null，这个超时时间可以通过RabbitTemplate.setReplyTimeout(10000)设置

