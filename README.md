## Docker Command 
* Build a docker image from the built code
```
docker build -t boot-example-docker.jar .
```

* Check the image has been created
```
docker image ls
```
this will show something like
```
REPOSITORY                TAG       IMAGE ID       CREATED          SIZE
boot-example-docker.jar   latest    a7f069c63be1   15 seconds ago   491MB
```
* Run the image on the container
9090 is the accessting port from external of docker
```
docker run -p 9090:8080 boot-example-docker.jar
```

## Docker hub
* To login
```
$ docker login
Authenticating with existing credentials...
Login Succeeded
```
* Check the image you have
```
$ docker image ls
REPOSITORY                TAG       IMAGE ID       CREATED             SIZE
boot-example-docker.jar   latest    a7f069c63be1   About an hour ago   491MB
```
* Create a tag that should go up on Docker Hub
```
$ docker tag boot-example-docker.jar allthatjava/boot-example-docker.jar
```
* Push the tagged image to Docker hub
```
$ docker push allthatjava/boot-example-docker.jar
Using default tag: latest
The push refers to repository [docker.io/allthatjava/boot-example-docker.jar]
5f83894a219f: Preparing
dc9fa3d8b576: Preparing
27ee19dc88f2: Preparing
c8dd97366670: Preparing
dc9fa3d8b576: Mounted from library/openjdk
c8dd97366670: Mounted from library/openjdk
27ee19dc88f2: Mounted from library/openjdk
5f83894a219f: Pushed
latest: digest: sha256:e14bfe9e22ac2baa29b6cf7a90577e0bc4657b2af350b3bbf3fea300ae1ae75e size: 1166
```
* Image has been uploaded to Docker hub. To pull the image down from Docker hub
```
$ docker pull allthatjava/boot-example-docker.jar
Using default tag: latest
latest: Pulling from allthatjava/boot-example-docker.jar
Digest: sha256:e14bfe9e22ac2baa29b6cf7a90577e0bc4657b2af350b3bbf3fea300ae1ae75e
Status: Image is up to date for allthatjava/boot-example-docker.jar:latest
docker.io/allthatjava/boot-example-docker.jar:latest
```

* To run the pulled image on Docker
```
$ docker run -p 9090:8080 allthatjava/boot-example-docker.jar
```
* Then access `http://localhost:9090/message` on the browser