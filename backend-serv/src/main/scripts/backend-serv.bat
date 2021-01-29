m2=${M2_REPO}
clsspth=src/main/resources
clsspth+=${clsspth}:${m2}episen/si/ing1/pds/backend-serv/1.0-SNAPSHOT/backend-serv-1.0-SNAPSHOT.jar
exec java -cp ${clsspth} espisen.si.ing1.pds.server.ServiceBackendMain $*