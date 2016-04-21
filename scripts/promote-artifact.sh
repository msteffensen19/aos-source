#!/bin/bash
set -e
SCRIPTNAME=$(basename $0)
SCRIPTDIR=$(readlink -m $(dirname $0))
RELEASEURL="http://mydtbld0028.hpeswlab.net:8081/nexus/content/repositories/adm-demo-app-release"
SNAPSHOTURL="http://mydtbld0028.hpeswlab.net:8081/nexus/service/local/artifact/maven/redirect?r=adm-demo-app-snapshot&g=com.advantage.online.store&a=pack_wars&e=zip&v=LATEST"
GROUPID="com.advantage.online.store"
REPOID="adm-demo-app-release"
SEARCH="pack_wars"
EXT="zip"
MVN=$(which mvn)
GEN="true"

function get_artifact {
	/usr/bin/wget --content-disposition $SNAPSHOTURL -q || { echo "${FUNCNAME[0]} failed"; exit 1; }
}

function rename_file {
	PACKFILE=$(/usr/bin/find ./$SEARCH-[0-9]*-[0-9]*$EXT | sort -t= -nr | head -n1)
  	NAME=$(basename $PACKFILE | cut -d '-' -f1-2)
  	VERSION=$(echo $NAME | cut -d '-' -f2)
  	FILE=$NAME.$EXT
  	/usr/bin/mv $PACKFILE $FILE || { echo "${FUNCNAME[0]} failed"; exit 1; }
}

function upload_artifact {
	$MVN deploy:deploy-file -DgroupId=$GROUPID -DartifactId=$NAME -Dversion=$VERSION -DgeneratePom=$GEN\
  	-Dpackaging=$EXT -DrepositoryId=$REPOID -Durl=$RELEASEURL\
  	-Dfile=$FILE -q || { echo "${FUNCNAME[0]} FAILED"; exit 1; }
}

get_artifact
rename_file
upload_artifact