mvn -DskipTests clean package
docker image build -t tt1/simserver .

docker-compose up

docker-compose down -v
docker image remove tt1/simserver