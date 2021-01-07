cd src/ &&

javac -d ../bin/ test/acceptance/Homepage*.java &&
javac -d ../bin/ test/acceptance/ConfigModelS*.java &&
javac -d ../bin/ test/acceptance/Evenement*.java &&
javac -d ../bin/ test/acceptance/CaracteristicsTab*.java &&

cd ../bin &&

#java org.junit.runner.JUnitCore test.acceptance.HomepageTest &&
#java org.junit.runner.JUnitCore test.acceptance.ConfigModelSTest &&
java org.junit.runner.JUnitCore test.acceptance.EvenementTest
#java org.junit.runner.JUnitCore test.acceptance.CaracteristicsTabTest
