#!/bin/bash

sudo apt-get update && sudo apt-get install -y \
    build-essential \
    git \
    postgresql-server-dev-all

git clone https://github.com/pgvector/pgvector.git /tmp/pgvector
cd /tmp/pgvector
make
sudo make install

sudo apt-get remove -y build-essential git postgresql-server-dev-all
sudo apt-get autoremove -y
sudo rm -rf /var/lib/apt/lists/* /tmp/pgvector

psql -U actor-linker -d actor-linker -c "CREATE EXTENSION vector;"