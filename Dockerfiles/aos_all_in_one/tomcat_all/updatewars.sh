#!/bin/bash
mkdir "tmp"
unzip "wars.zip" -d "./tmp"
cd "./tmp"
mkdir "Temp"
x=`find . -iname "*.war" | cut -d/ -f2`
for i in $x
do
chmod 777 $i
ls -l
echo "for ------> $i"
mv $i "./Temp"
cd "./Temp"
jar -xf $i
#sed -i 's/\.\./\/opt/g' "./WEB-INF/classes/log4j.properties"
path_to_services="./WEB-INF/classes/properties/services.properties"
if [ "$i" == "ROOT.war" ];then
  path_to_services="./services.properties"
fi
command1="sed -i 's/=localhost/=${HOST_IP}/g' ${path_to_services}"
command2="sed -i 's/account\.soapservice\.url\.port=8080/account\.soapservice\.url\.port=${ACCOUNT_PORT}/g' ${path_to_services}"
command3="sed -i 's/=8080/=${WARS_PORT}/g' ${path_to_services}"
sed -i 's/single\.machine\.deployment=true/single\.machine\.deployment=false/g' "${path_to_services}"
eval $command1
eval $command2
eval $command3
if [[ "$i" == "order.war" || "$i" == "catalog.war" ]];then
#sed -i 's/=validate/=create/g' "./WEB-INF/classes/properties/internal_config_for_env.properties"
   command3="sed -i 's/=localhost/=${HOST_IP}/g' ./WEB-INF/classes/properties/internal_config_for_env.properties"
   command4="sed -i 's/=5432/=${POSTGRES_PORT}/g' ./WEB-INF/classes/properties/internal_config_for_env.properties"
   eval $command3
   eval $command4
fi
rm -rf $i
jar -cvf $i *
mv $i ../
rm -rf *
cd ../
done

rm -rf "Temp/"
mv * ../
cd ../

