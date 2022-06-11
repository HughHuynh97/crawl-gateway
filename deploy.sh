app="database"
app2="crawl-service"
app3="cloud-gateway"
username="trivip002"

if [[ -n "$( docker container -q mysqldb )" ]]; then
  echo "MYSQL Container exists"
else
  docker create -p 3307:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=tripro123  -e MYSQL_DATABASE=dev mysql:8.0.29
fi
docker network rm spring-net
docker network create spring-net
docker network connect spring-net mysqldb

docker stop "$app" && docker rm -f "$app"
docker pull "$username"/"$app":latest

docker stop "$app2" && docker rm -f "$app2"
docker pull "$username"/"$app2":latest

docker stop "$app3" && docker rm -f "$app3"
docker pull "$username"/"$app3":latesta

docker create -p 8081:8081 --name "$app" --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USERNAME=root -e MYSQL_PASSWORD=tripro123 -e MYSQL_PORT=3306 -e MYSQL_DATABASE=dev "$username"/"$app"
docker create -p 8082:8082 --name "$app2" --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USERNAME=root -e MYSQL_PASSWORD=tripro123 -e MYSQL_PORT=3306 -e MYSQL_DATABASE=dev "$username"/"$app2"
docker run -p 8080:8080 --name cloud-gateway --net spring-net -e DATABASE=http://"$app":8081 cloud-gateway -e CRAWL_SERVICE=http://"$app2":8082

container start mysqldb

echo Deploy Done !
