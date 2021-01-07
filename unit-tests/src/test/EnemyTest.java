package test;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.allOf;

import codingfactory.rpgconsole.hero.Hero;
import codingfactory.rpgconsole.enemy.Enemy;

public class EnemyTest {

    Hero hero;
    Enemy enemy;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("Avant le démarrage");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("Après tous les tests");
    }

    @Before
    public void setUp() throws Exception {
        hero = new Hero("Pierre");
        enemy = new Enemy ("anthony", 1);
        System.out.println("Avant un test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Après un test");
    }

    @Test
    public void testHeroProperties() throws Exception {
        assertThat(enemy, hasProperty("name"));
        assertThat(enemy, hasProperty("name", is("anthony")));
    }

    @Test
    public void testEnemyGetName() throws Exception {
        assertThat(enemy.getName(), is("anthony"));
    }

    @Test
    public void testEnemyGetHp() throws Exception {
        assertThat(enemy.getHp(), is(15));
    }

    @Test
    public void testEnemyTakeDamage() throws Exception {
        enemy.takeDamage(10);
        assertThat(enemy.getHp(), is(5));
    }

    @Test
    public void testEnemyAttack() throws Exception {
        enemy.attack(hero);
        assertThat(hero.getHp(), allOf(greaterThan(16), lessThan(20)));
    }
    
}