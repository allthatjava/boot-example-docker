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

### EXTRA - Build docker image without Dockerfile
* By the running the following command, maven will generate Docker image based on the project
```
mvn clean package spring-boot:build-image
```
* To run the maven generated Docker image
```
docker run --tty --publish 8080:8080 boot-example-docker:0.0.1-SNAPSHOT
```

# To run it on Kubernetes
* To run docker image with minikube following must be done
```xml
C:\Users\allth>minikube docker-env
        SET DOCKER_TLS_VERIFY=1
        SET DOCKER_HOST=tcp://127.0.0.1:56987
        SET DOCKER_CERT_PATH=C:\Users\allth\.minikube\certs
        SET MINIKUBE_ACTIVE_DOCKERD=minikube
        REM To point your shell to minikube's docker-daemon, run:
        REM @FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env --shell cmd') DO @%i
```
* then copy the last line and run it on the command line
```
C:\Users\allth>@FOR /f "tokens=*" %i IN ('minikube -p minikube docker-env --shell cmd') DO @%i
```
* You gotta start the minikube first
  `minikube start --driver=docker`
 
* create a `deployment.yaml` file with following content
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: boot-example-docker-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: boot-example-docker
  template:
    metadata:
      labels:
        app: boot-example-docker
    spec:
      containers:
        - name: boot-example-docker
          image: boot-example-docker:1.0
          ports:
            - containerPort: 8080
```
* then create `service.yaml` with following contents
```yaml
apiVersion: v1
kind: Service
metadata:
  name: boot-example-docker-service
spec:
  type: ClusterIP
  selector:
    app: boot-example-docker
  ports:
    - port: 8080
      targetPort: 8080
```
* then run the following command to deploy the app
`kubectl apply -f deployment.yaml`
* then run the following command to register service
`kubctl apply -f service.yaml`
* then run the following command to start the app
`minikube service boot-example-docker-service`

* If you want to run the applicaiton on expose certain port, you need to use port-forwarding with pods name
`kubectl port-forward boot-example-docker-deployment-5d85685967-g299n 8080:8080`


### Deploy it to Azure Container
* Add code to Azure DevOps (Or Azure DevOps supported repository).
* Then select "Pipelines > Azure Repos Git"
![img/azure-pipeline-01.png](img/azure-pipeline-01.png)
* Select repository you are going to use
![img/azure-pipeline-02.png](img/azure-pipeline-02.png)
* Select Docker with Conatiner Registry option
![img/azure-pipeline-03.png](img/azure-pipeline-03.png)
* Azure login popup will come up and sign in
* Then select Container Registry you have created in Azure Portal and Dockerfile
![img/azure-pipeline-04.png](img/azure-pipeline-04.png)
* Save and run
![img/azure-pipeline-05.png](img/azure-pipeline-05.png)
* It will fail for the first then since there are no maven task added
![img/azure-pipeline-06.png](img/azure-pipeline-06.png)
* Select the pipeline you created and edit
* Search for the "maven" in the Tasks search box.
![img/azure-pipeline-07.png](img/azure-pipeline-07.png)
* Create additional line after "Steps:". Then "Add" maven task configure as you would normally do. such as goal to `install`
![img/azure-pipeline-08.png](img/azure-pipeline-08.png)
* Validate and Run
![img/azure-pipeline-09.png](img/azure-pipeline-09.png)
* It will build successfully and push the image to Container Registry
![img/azure-pipeline-10.png](img/azure-pipeline-10.png)
* You will see the pushed image on Container Registry
![img/azure-pipeline-11.png](img/azure-pipeline-11.png)
* When you clicked image name, you will see the build version
![img/azure-pipeline-12.png](img/azure-pipeline-12.png)
* Now create a web app with container option
  ![img/azure-pipeline-13.png](img/azure-pipeline-13.png)
* In the container step, select "Azure Container Registry" then select the image and version you want to deploy
  ![img/azure-pipeline-14.png](img/azure-pipeline-14.png)
* In the overview of web app, you will see the Default domain, click that
  ![img/azure-pipeline-15.png](img/azure-pipeline-15.png)
* You will see the app is running
  ![img/azure-pipeline-16.png](img/azure-pipeline-16.png)

# Extra
* To change the log level, run the following command on the command line tool
`curl -i -X POST -H 'Content-Type: application/json' -d '{"configuredLevel":"TRACE"}' http://localhost:8080/actuator/loggers/brian.example `
* Then you should be able to check the log level with following command
`curl http://localhost:8080/actuator/loggers/brian.example`