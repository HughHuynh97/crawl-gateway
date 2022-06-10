app="database"
app2="crawl-service"
username="trivip002"

if [[ -n "$( docker network -q spring-net )" ]]; then
  echo "MYSQL Container exists"
else
  docker create -p 3307:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=tripro123  -e MYSQL_DATABASE=dev mysql:8.0.29
fi
docker network rm spring-net
docker network create spring-net
docker stop "$app" && docker rm -f "$app"
docker pull "$username"/"$app":latest
docker stop "$app2" && docker rm -f "$app2"
docker pull "$username"/"$app2":latest
docker create -p 8081:8081 --name "$app" --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USERNAME=root -e MYSQL_PASSWORD=tripro123 -e MYSQL_PORT=3306 -e MYSQL_DATABASE=dev "$username"/"$app"
docker create -p 8080:8080 --name "$app2" --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USERNAME=root -e MYSQL_PASSWORD=tripro123 -e MYSQL_PORT=3306 -e MYSQL_DATABASE=dev "$username"/"$app2"
docker network connect spring-net mysqldb
docker container start mysqldb
sleep 6
echo Begin Start Module "$app"
docker container start "$app"
sleep 6
echo Begin Start Module "$app2"
docker container start "$app2"

echo Deploy Done !
