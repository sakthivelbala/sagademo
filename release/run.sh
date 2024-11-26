
java -jar zipkin.jar &
java -jar registry.jar &

sleep 15

java -jar cart.jar &
java -jar order.jar &
java -jar inventory.jar &
java -jar payment.jar &

sleep 15

java -jar gateway.jar &