cd src &&

javac -d ../bin/ codingfactory/rpgconsole/enemy/*.java &&
javac -d ../bin/ codingfactory/rpgconsole/hero/*.java &&
javac -d ../bin/ codingfactory/rpgconsole/game/*.java &&

javac -d ../bin/ test/HeroTest.java &&
javac -d ../bin/ test/EnemyTest.java &&

cd ../bin &&

java org.junit.runner.JUnitCore test.HeroTest &&
java org.junit.runner.JUnitCore test.EnemyTest
