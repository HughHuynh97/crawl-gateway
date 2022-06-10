app="crawl-service"
username="trivip002"
docker network prune
docker network create spring-net
docker network connect spring-net mysqldb
echo Pull Image
docker stop "$app" && docker rm -f "$app"
docker pull "$username"/"$app":latest
echo Create Container
docker create -p 8080:8080 --name "$app" --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USERNAME=root -e MYSQL_PASSWORD=tripro123 -e MYSQL_PORT=3306 -e MYSQL_DATABASE=dev "$username"/"$app"
