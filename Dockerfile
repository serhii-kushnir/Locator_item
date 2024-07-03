FROM postgres:16.0

ARG DB_USERNAME
ARG DB_PASSWORD
ARG POSTGRES_DB

ENV POSTGRES_USER=${DB_USERNAME}
ENV POSTGRES_PASSWORD=${DB_PASSWORD}
ENV POSTGRES_DB=${POSTGRES_DB}


# docker build --build-arg DB_USERNAME= --build-arg DB_PASSWORD= --build-arg POSTGRES_DB= -t skproject .
# docker run --name db_skproject -d -p 5434:5432 skproject

# docker start db_skproject
# docker stop db_skproject