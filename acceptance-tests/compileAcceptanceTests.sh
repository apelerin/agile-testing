cd src/ &&

javac -d ../bin/ test/acceptance/Homepage*.java &&
javac -d ../bin/ test/acceptance/ConfigModelS*.java &&

cd ../bin &&
#java org.junit.runner.JUnitCore test.acceptance.HomepageTest &&

java org.junit.runner.JUnitCore test.acceptance.ConfigModelSTest
