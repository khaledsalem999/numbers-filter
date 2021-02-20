# Deployment
This is the deployment guide for the project :<br/>
There will be 2 ways explained here to deploy the app, all will be explained assuming you have docker installed. The first which is simple but won't run the whole BE and FE package togther, however the 2nd will require some work first but then will deploy the whole package in a single line so let's get started

## seperate method:
For the backend simply get into the folder and then run `mvnw compile jib:dockerBuild -Dimage=jumia-be test` 
this will directly create an image locally for docker to run with all tests. To run the BE app inside a container simply run `docker run -d -p 8080:8080 jumia-be` and that's it for the frontend.
To test that it's working correctly on postman try calling `http://localhost:8080/api/backend/customers?page=0&size=100` it should return the entire customers list.

As for the frontend, get inside the frontend folder and run `docker build -t jumia-fe .` to create the frontend image, and to run it simply execute `docker run -d -p 4200:4200 jumia-fe` and you will the FE project ready

No further configurations needed for this method, just jump to `http://localhost:4200` and enjoy. I haven't used a dockerfile for the BE because the jib method a lot more accurate and faster, however dockerfiles were used normally for the FE.


## skaffold method:
alright here where things gets a bit more fun, assuming you are on mac or windows, you already have docker desktop, so just enable kubernetes clusters and that's it, if you are on linux and already have docker installed, just run all of these


`curl -sfL https://get.k3s.io | sh -s - --docker --no-deploy traefik --write-kubeconfig-mode 644`

`echo "export KUBECONFIG=/etc/rancher/k3s/k3s.yaml" >> /etc/profile`

`echo "export KUBECONFIG=/etc/rancher/k3s/k3s.yaml" >> $RUN_BY_HOME/.profile`

`echo 'Defaults env_keep += "KUBECONFIG"' > /etc/sudoers.d/100_kubeconfig`

`chmod 666 /etc/rancher/k3s/k3s.yaml`


run `kubectl` to make sure it's installed correctly, now that we have enabled/installed kube, you need to pull ingress

`kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/mandatory.yaml`

`kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/provider/cloud-generic.yaml`

after running those now only one thing left, downloading skaffold
https://skaffold.dev/docs/install/ , if you are on windows do it using choco to automatically configure the enviroment variables for it, it's pretty much straigh forward for other OSs

now that this is all done and configured, in the root folder of the project that contains both the FE and BE project, just run `skaffold dev -f jumia-demo.yaml` aaaand that's it :)

just enter `http://localhost/jumiademo` in your browser and enjoy, and if you want to test the BE on postman `http://localhost/api/backend/customers?page=0&size=100`

this method will bascially just deploy everything at once with BE unit tests as well, you can say it's a low scale azure pipelines on local PC

## REST APIs and notes
I have only created one API with all filters as parameters, the api is `/api/backend/customers`, the api parameters are mainly 2 filters and 2 pagination values, the first parameter is for the state `stateFilter` 
it only takes `valid` or `invalid`, 
the second filter is country filter `countryFilter` it takes all listed countries that was included in the PDF packed with the database, you can include multiple countries at once in a single string like this `cameroon&ethiopia&uganda` and will display all numbers related to those countries, of course as mentioned
in the PDF, they are all filtered by country code, and you can use both filters at once, as for pagination params they are straight forward `page` represents the page number starting from `0`, and `size` is how many items per page.

## Tools and frameworks used

Languages :<br/>
Java 11<br/>
Typescript<br/>
HTML5/SCSS<br/>

IDEs and text editors : <br/>
Intellij idea <br/>
webstorm <br/>
visual studio code <br/>

Frameworks : <br/>
Spring boot<br/>
Angular 9<br/>

Deployment tools : <br/>
Docker <br/>
Kubernetes <br/>
skaffold <br/>
