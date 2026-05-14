cd ../Trabajo_grupal-WebApp
docker image build -t tt1/simwebapp .
cd ../Trabajo_grupal
docker image build -t tt1/simserver .
docker-compose -f docker-compose_with-simwebapp.yml up
docker-compose down -v
docker image remove tt1/simwebapp
docker image remove tt1/simserver