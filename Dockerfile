FROM postgres:latest

RUN apt-get update && apt-get install -y \
    build-essential \
    git \
    postgresql-server-dev-all

RUN git clone https://github.com/pgvector/pgvector.git /tmp/pgvector && \
    cd /tmp/pgvector && \
    make && \
    make install

RUN apt-get remove -y build-essential git postgresql-server-dev-all && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/* /tmp/pgvector