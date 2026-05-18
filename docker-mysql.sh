docker run -d \
  --name simserver_mysql \
  -e 'MYSQL_ROOT_PASSWORD=MWda2fh1JmmwOthc' \
  -e 'MYSQL_DATABASE=simserver_db' \
  -e 'MYSQL_USER=sim_user' \
  -e 'MYSQL_PASSWORD=eSOJJ09xVJqtyjxv' \
  -v '$(pwd)/database/init.sql:/docker-entrypoint-initdb.d/init.sql' \
  -v 'mysql_data:/var/lib/mysql' \
  -p '3306:3306' \
  mysql:8.0