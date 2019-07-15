package com.bb.cs.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryCustomerRepositoryTest {

    @Test
    public void testDeleteAddCount() {
        InMemoryCustomerRepository r = new InMemoryCustomerRepository();
        r.deleteAll();

        Assert.isTrue(r.count()==0, "Count should be 0 after deleteAll()");
    }

    @Test
    public void testAddAndCount() {
        InMemoryCustomerRepository r = new InMemoryCustomerRepository();
        r.deleteAll();

        Customer c1 = new Customer("Agent", "Smith");
        Customer c2 = r.save(c1);

        Assert.isTrue(c2.getId()>=0, "Id should not be a negative number after add operation.");

        Assert.isTrue(c1.getTitle().equals(c2.getTitle()), "titles should be the same");
        Assert.isTrue(c1.getName().equals(c2.getName()), "names should be the same");


        Assert.isTrue(r.count()==1, "Count should be 1 after adding one element");

        Customer c3 = new Customer("Mr", "Neo");
        Customer c4 = r.save(c3);
        Assert.isTrue(r.count()==2, "Count should be 2 after adding 2 elements");
    }

    @Test
    public void testAdd() {
        InMemoryCustomerRepository r = new InMemoryCustomerRepository();
        long origSize = r.count();

        Customer c1 = new Customer("Agent", "Smith");
        Customer c2 = r.save(c1);
        Assert.isTrue(r.count() == origSize+1, "Size should increase after adding an element");

        Optional<Customer> element = r.findById(c2.getId());
        Assert.isTrue(!element.isEmpty(), "Element should be there after adding it to the repository");
        Assert.isTrue(c1.getName().equals(c2.getName()), "names should be the same");


        Customer c3 = new Customer("Mr", "Neo");
        Customer c4 = r.save(c3);
        Assert.isTrue(r.count()==origSize+2, "Count should be original size + 2");


        Assert.isTrue(r.findById(c4.getId()).get().getId() == c4.getId(), "Find should return and element with the same IDsame");
    }

    @Test
    public void testModify() {
        InMemoryCustomerRepository r = new InMemoryCustomerRepository();

        Customer c1 = new Customer("Mr", "Anderson");
        Customer c2 = r.save(c1);
        long oldSize = r.count();


        Customer c3 = new Customer("Mr", "Neo");
        c3.setId(c2.getId());
        r.save(c3);

        Assert.isTrue(oldSize == r.count(), "Size of repository should be the same after renaming Mr Anderson to Neo.");

        Customer c4 = r.findById(c3.getId()).get();

        Assert.isTrue(c4.getTitle().equals("Mr"), "the new title is expected");
        Assert.isTrue(c4.getName().equals("Neo"), "the new name is expected");
    }
}
