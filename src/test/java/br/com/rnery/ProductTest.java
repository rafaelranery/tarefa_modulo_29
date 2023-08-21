package br.com.rnery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.rnery.dao.generic.jdbc.dao.IProductDAO;
import br.com.rnery.dao.generic.jdbc.dao.ProductDAO;
import br.com.rnery.domain.Product;

public class ProductTest {
	
	private IProductDAO dao;
	
	@Before
	public void init() {
		dao = new ProductDAO();
	}
	
	@Test
	public void registerTest() throws Exception {
		Product p = new Product();
		p.setName("Product test");
		p.setCode("10");
		p.setPrice(25d);
		
		Integer registerCount = dao.register(p);
		assertTrue(registerCount == 1);
		
		Product pDB = dao.search("10");
		assertNotNull(pDB);
		assertEquals(p.getName(), pDB.getName());
		assertEquals(p.getCode(), pDB.getCode());
		
		Integer deleteCount = dao.delete(pDB);
		assertTrue(deleteCount == 1);
	}
	
	@Test
	public void searchTest() throws Exception {
		Product p = new Product();
		p.setName("Product test");
		p.setCode("10");
		p.setPrice(25d);
		
		Integer registerCount = dao.register(p);
		assertTrue(registerCount == 1);
		
		Product pDB = dao.search("10");
		assertNotNull(pDB);
		assertEquals(p.getName(), pDB.getName());
		assertEquals(p.getCode(), pDB.getCode());
		
		Integer deleteCount = dao.delete(pDB);
		assertTrue(deleteCount == 1);
	}
	
	@Test
	public void searchAllTest() throws Exception {
		Product p = new Product();
		p.setName("Product test");
		p.setCode("10");
		p.setPrice(25d);
		
		Product p2 = new Product();
		p2.setName("Product test 2");
		p2.setCode("20");
		p2.setPrice(30d);
		
		List<Product> listLocal = List.of(p, p2);
		
		Integer registerCount = 0;
		for(Product pL : listLocal) {
			Integer res = dao.register(pL);
			registerCount += res;
		}
		assertTrue(registerCount == 2);
		
		List<Product> listDB = dao.getAll();
		assertNotNull(listDB);
		assertTrue(listDB.size() == 2);
		
		for(Integer i = 0; i < listDB.size(); i++) {
			assertEquals(listLocal.get(i).getCode(), listDB.get(i).getCode());
			assertEquals(listLocal.get(i).getName(), listDB.get(i).getName());
			assertEquals(listLocal.get(i).getPrice(), listDB.get(i).getPrice());
		}
		
		Integer countDelete = 0;
		for(Product pDB : listDB) {
			Integer res = dao.delete(pDB);
			countDelete += res;
		}
		assertTrue(countDelete == 2);
		
		for(Product pL : listLocal) {
			Product pRes = dao.search(pL.getCode());
			assertNull(pRes);
		}
	}
	
	@Test 
	public void deleteTest() throws Exception {
		Product p = new Product();
		p.setName("Product test");
		p.setCode("10");
		p.setPrice(25d);
		
		Integer registerCount = dao.register(p);
		assertTrue(registerCount == 1);
		
		Product pDB = dao.search("10");
		assertNotNull(pDB);
		assertEquals(p.getName(), pDB.getName());
		assertEquals(p.getCode(), pDB.getCode());
		
		Integer deleteCount = dao.delete(pDB);
		assertTrue(deleteCount == 1);
	}
	
	@Test
	public void updateTest() throws Exception {
		Product p = new Product();
		p.setName("Product test");
		p.setCode("10");
		p.setPrice(25d);
		
		Integer registerCount = dao.register(p);
		assertTrue(registerCount == 1);
		
		Product pDB = dao.search("10");
		assertNotNull(pDB);
		assertEquals(p.getName(), pDB.getName());
		assertEquals(p.getCode(), pDB.getCode());
		
		pDB.setCode("20");
		pDB.setName("Product Test Updated");
		pDB.setPrice(45d);
		
		Integer updateCount = dao.update(pDB);
		assertTrue(updateCount == 1);
		
		Product pDBUpdated = dao.search("20");
		assertNotNull(pDBUpdated);
		assertEquals(pDB.getName(), pDBUpdated.getName());
		assertEquals(pDB.getCode(), pDBUpdated.getCode());
		assertEquals(pDB.getId(), pDBUpdated.getId());

		Integer deleteCount = dao.delete(pDBUpdated);
		assertTrue(deleteCount == 1);
	}
}
