package br.com.rnery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.rnery.dao.generic.jdbc.dao.ClientDAO;
import br.com.rnery.dao.generic.jdbc.dao.IClientDAO;
import br.com.rnery.domain.Client;

public class ClientTest {
	private IClientDAO dao;
	
	@Before
	public void init() {
		dao = new ClientDAO();
	}
	
	@Test
	public void registerTest() throws Exception {
		Client c = new Client();
		c.setName("Name 1");
		c.setCode("10");
		
		Integer countResgistered = dao.register(c);
		assertTrue(countResgistered == 1);
		
		Client cDB = dao.search("10");
		assertNotNull(cDB);
		assertEquals(c.getName(), cDB.getName());
		assertEquals(c.getCode(), cDB.getCode());
		
		Integer countDel = dao.delete(cDB);
		assertTrue(countDel == 1);
	}
	
	@Test
	public void searchTest() throws Exception {
		Client c = new Client();
		c.setName("Name 1");
		c.setCode("10");
		
		Integer countResgistered = dao.register(c);
		assertTrue(countResgistered == 1);
		
		Client cDB = dao.search("10");
		assertNotNull(cDB);
		assertEquals(c.getName(), cDB.getName());
		assertEquals(c.getCode(), cDB.getCode());
		
		Integer countDel = dao.delete(cDB);
		assertTrue(countDel == 1);
	}
	
	@Test 
	public void getAllTest() throws Exception {
		Client c1 = new Client();
		Client c2 = new Client();
		
		c1.setCode("10");
		c1.setName("Name 1");
		c2.setCode("11");
		c2.setName("Name 2");
		
		List<Client> listLocal = List.of(c1, c2);
		Integer countCad = 0;
		for(Client c : listLocal) {
			Integer res = dao.register(c);
			countCad += res;
		}
		assertTrue(countCad == 2);
		
		List<Client> listDB = dao.getAll();
		assertNotNull(listDB);
		assertEquals(2, listDB.size());
		
		for (Integer i = 0; i < listDB.size(); i++) {
			assertEquals(listLocal.get(i).getCode(), listDB.get(i).getCode());
			assertEquals(listLocal.get(i).getName(), listDB.get(i).getName());
		}
		
		Integer countDel = 0;
		for(Client c : listDB) {
			Integer res = dao.delete(c);
			countDel += res;
		}
		assertEquals((Integer) 2, countDel);
	}
	
	@Test
	public void updateTest() throws Exception {
		Client c = new Client();
		c.setName("Name 1");
		c.setCode("10");
		
		Integer countResgistered = dao.register(c);
		assertTrue(countResgistered == 1);
		
		Client cDB = dao.search("10");
		assertNotNull(cDB);
		assertEquals(c.getName(), cDB.getName());
		assertEquals(c.getCode(), cDB.getCode());
		
		
		cDB.setCode("20");
		cDB.setName("Name Update");
		
		Integer countUpdated = dao.update(cDB);
		assertTrue(countUpdated == 1);
		
		Client cDB2 = dao.search("20");
		assertNotNull(cDB2);
		assertEquals(cDB.getName(), cDB2.getName());
		assertEquals(cDB.getCode(), cDB2.getCode());
		assertEquals(cDB.getId(), cDB2.getId());
		
		Integer countDel = dao.delete(cDB2);
		assertTrue(countDel == 1);
	}
	
}
