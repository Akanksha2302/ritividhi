mvn clean package
docker build -t ritividhi-app:latest . --no-cache

docker rm -f ritividhi_app
docker run -d --net=host --restart=always --name=ritividhi_app ritividhi-app:latest