FROM node:alpine

WORKDIR /src

RUN npm install -g @angular/cli
ADD package.json package.json
RUN npm install

ADD . /src

ENTRYPOINT ng serve -c=skaffold --host 0.0.0.0 --port 8080 --disableHostCheck=true --baseHref=/jumiademo/ --servePath=/ --deployUrl=/jumiademo/ --publicHost=http://0.0.0.0/jumiademo/
