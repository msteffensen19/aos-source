#!/bin/bash
sed -i 's/\.cmd//g' root/pom.xml
sed -i 's/<argument>install<\/argument>/<argument>--allow-root<\/argument>\n&/' root/pom.xml
