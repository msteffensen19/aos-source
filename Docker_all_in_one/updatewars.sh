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
sed -i 's/\.\./\/opt/g' "./WEB-INF/classes/log4j.properties"
if [ "$i" != "ROOT.war" ];then
   sed -i 's/=validate/=create/g' "./WEB-INF/classes/properties/internal_config_for_env.properties"
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
rm -rf "tmp/"
