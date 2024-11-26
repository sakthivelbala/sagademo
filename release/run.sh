
java -jar ./release/zipkin.jar &
java -jar ./release/registry.jar &

sleep 10

java -jar ./release/gateway.jar &
java -jar ./release/cart.jar &
java -jar ./release/order.jar &
java -jar ./release/inventory.jar &
java -jar ./release/payment.jar &

