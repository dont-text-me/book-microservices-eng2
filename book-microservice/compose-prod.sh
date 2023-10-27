#!/bin/bash

docker compose -p book-microservice-prod -f compose-prod-secrets.yml -f compose-prod.yml up -d