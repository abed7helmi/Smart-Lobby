#!/bin/bash
classp="${M2_REPO}/episen/si/ing1/pds/client/1.0-SNAPSHOT"
filejar=${classp}"/client-1.0-SNAPSHOT-jar-with-dependencies.jar"
#echo $M2_REPO
exec java -classpath ${filejar} episen.si.ing1.pds.client.Indicators.test.IndicatorsTest ${*}
