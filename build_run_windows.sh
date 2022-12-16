echo "Clearing old build files..."
rm -r bin
mkdir bin
echo "Done"
echo "Compiling..."
find ./src/* | grep .java > sources.txt
javac -encoding utf-8 -d bin -cp ".;lib/sqlite-jdbc-3.36.0.3.jar;lib/forms_rt-7.0.3.jar" "@sources.txt"
echo "Done"
echo "Generating Jar Package"
# Generate Manifest
cat 'MANIFEST.MF'
# Generate jar file
jar -cvfm 'FancyBank611.jar' 'MANIFEST.MF' -C 'bin' .
echo "Done"