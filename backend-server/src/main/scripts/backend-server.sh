classp="${M2_REPO}/episen/si/ing1/pds/backend-server/1.0-SNAPSHOT"
filejar=${classp}"/backend-server-1.0-SNAPSHOT-jar-with-dependencies.jar"
#echo $M2_REPO
exec java -classpath ${filejar} episen.si.ing1.pds.backend.server.BackendServer ${*}
