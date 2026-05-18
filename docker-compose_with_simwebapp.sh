cd ../Trabajo_grupal-WebApp
mvn -DskipTests clean package
docker image build -t tt1/simwebapp .

cd ../Trabajo_grupal
mvn -DskipTests clean package
docker image build -t tt1/simserver .

docker-compose -f docker-compose_with_simwebapp.yml up

docker-compose -f docker-compose_with_simwebapp.yml down -v
docker image remove tt1/simwebapp
docker image remove tt1/simserver