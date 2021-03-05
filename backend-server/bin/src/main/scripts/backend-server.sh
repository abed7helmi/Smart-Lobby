classp=${M2_REPO}
filejar=${classp}"/episen/si/ing1/pds/backend-server/1.0-SNAPSHOT/backend-server-1.0-SNAPSHOT-jar-with-dependencies.jar"
#echo $M2_REPO
exec java -classpath ${filejar} episen.si.ing1.pds.backend.server.BackendServer ${*}
