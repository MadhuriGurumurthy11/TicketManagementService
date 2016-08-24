/**
 * 
 */
package TicketManagement.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import TicketManagement.dao.SeatHold;

/**
 * @author Madhuri Gurumurthy
 *
 *         This is a test class for TicketQueryImpl
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TicketQueryImpl.class, HibernateUtil.class })
public class TicketQueryImplTest
{
	private TicketQueryImpl ticketQuery;
	SeatHold seatHold = Mockito.mock(SeatHold.class);

	SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
	Session session = Mockito.mock(Session.class);
	Transaction transaction = Mockito.mock(Transaction.class);
	Query query = Mockito.mock(Query.class);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		ticketQuery = new TicketQueryImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		ticketQuery = null;
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#getTotalAvailableSeats()}
	 * .
	 */
	@Test
	public void testGetTotalAvailableSeatsCase1()
	{
		PowerMockito.mockStatic(HibernateUtil.class);

		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when(
				session.createQuery("select totalAvailableSeats from LevelInfo"))
				.thenReturn(query);
		List<Integer> seatsList = new ArrayList<Integer>();
		seatsList.add(6214);
		Mockito.when(query.list()).thenReturn(seatsList);

		int seats = ticketQuery.getTotalAvailableSeats();

		assertEquals(6214, seats);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#getTotalAvailableSeats()}
	 * .
	 */
	@Test
	public void testGetTotalAvailableSeatsCase2()
	{
		PowerMockito.mockStatic(HibernateUtil.class);

		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when(
				session.createQuery("select totalAvailableSeats from LevelInfo"))
				.thenReturn(query);
		List<Integer> seatsList = new ArrayList<Integer>();
		seatsList.add(0);
		Mockito.when(query.list()).thenReturn(seatsList);

		int seats = ticketQuery.getTotalAvailableSeats();

		assertEquals(0, seats);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#getAvailSeatsOfALevel(int)}
	 * .
	 */
	@Test
	public void testGetAvailSeatsOfALevel()
	{
		PowerMockito.mockStatic(HibernateUtil.class);

		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when(
				session.createQuery("select totalAvailableSeats from LevelInfo where levelId=2"))
				.thenReturn(query);
		List<Integer> seatsList = new ArrayList<Integer>();
		seatsList.add(1100);
		Mockito.when(query.list()).thenReturn(seatsList);

		int seats = ticketQuery.getAvailSeatsOfALevel(2);

		assertEquals(1100, seats);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#findAndHoldSeats(int, int, int, java.lang.String)}
	 * .
	 * 
	 * @throws TicketManagementException
	 */
	@Test
	public void testFindAndHoldSeatsPositiveCase1()
			throws TicketManagementException
	{
		String customerEmail = "mg@gmail.com";
		int numSeats = 6;
		int minimumLevel = 1;
		int maximumLevel = 4;

		PowerMockito.mockStatic(HibernateUtil.class);
		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when(session.createQuery(Mockito.anyString()))
				.thenReturn(query);
		List<Object[]> levelSeatInfo = new ArrayList<Object[]>();
		Object[] obj1 = { 1, 12, 25, 50 };
		Object[] obj2 = { 2, 458, 25, 50 };
		Object[] obj3 = { 3, 700, 25, 50 };
		Object[] obj4 = { 4, 70, 25, 50 };
		levelSeatInfo.add(obj1);
		levelSeatInfo.add(obj2);
		levelSeatInfo.add(obj3);
		levelSeatInfo.add(obj4);

		List<Object[]> availSeatInfo = new ArrayList<Object[]>();
		Object[] obj5 = { 1, 1 };
		availSeatInfo.add(obj5);

		List<Integer> maxId = new ArrayList<Integer>();
		maxId.add(2);

		Mockito.when(query.list()).thenReturn(levelSeatInfo, availSeatInfo,
				maxId);

		Mockito.when((session).beginTransaction()).thenReturn(transaction);
		Mockito.when(query.executeUpdate()).thenReturn(1);
		Mockito.when((session).getTransaction()).thenReturn(transaction);

		SeatHold seatHold = ticketQuery.findAndHoldSeats(numSeats,
				minimumLevel, maximumLevel, customerEmail);
		assertEquals("1", seatHold.getRowNumber());
		assertEquals("1,2,3,4,5,6", seatHold.getSeatNumbers());
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#findAndHoldSeats(int, int, int, java.lang.String)}
	 * .
	 * 
	 * @throws TicketManagementException
	 */
	@Test
	public void testFindAndHoldSeatsPositiveCase2()
			throws TicketManagementException
	{
		String customerEmail = "mg@gmail.com";
		int numSeats = 3;
		int minimumLevel = 3;
		int maximumLevel = 4;

		PowerMockito.mockStatic(HibernateUtil.class);
		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when(session.createQuery(Mockito.anyString()))
				.thenReturn(query);
		List<Object[]> levelSeatInfo = new ArrayList<Object[]>();
		Object[] obj1 = { 1, 12, 25, 50 };
		Object[] obj2 = { 2, 458, 25, 50 };
		Object[] obj3 = { 3, 700, 25, 50 };
		Object[] obj4 = { 4, 70, 25, 50 };
		levelSeatInfo.add(obj1);
		levelSeatInfo.add(obj2);
		levelSeatInfo.add(obj3);
		levelSeatInfo.add(obj4);

		List<Object[]> availSeatInfo = new ArrayList<Object[]>();
		Object[] obj5 = { 4, 6 };
		availSeatInfo.add(obj5);

		List<Integer> maxId = new ArrayList<Integer>();
		maxId.add(2);

		Mockito.when(query.list()).thenReturn(levelSeatInfo, availSeatInfo,
				maxId);

		Mockito.when((session).beginTransaction()).thenReturn(transaction);
		Mockito.when(query.executeUpdate()).thenReturn(1);
		Mockito.when((session).getTransaction()).thenReturn(transaction);

		SeatHold seatHold = ticketQuery.findAndHoldSeats(numSeats,
				minimumLevel, maximumLevel, customerEmail);
		assertEquals("4", seatHold.getRowNumber());
		assertEquals("6,7,8", seatHold.getSeatNumbers());
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#reserveSeats(int, java.lang.String)}
	 * .
	 * 
	 * @throws TicketManagementException
	 */
	@Test
	public void testReserveSeats() throws TicketManagementException
	{
		int seatHoldId = 167;
		String customerEmail = "mg@gmail.com";

		PowerMockito.mockStatic(HibernateUtil.class);

		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when((session).beginTransaction()).thenReturn(transaction);

		Mockito.when(
				session.createQuery("update SeatHold set status='Reserved' ,confirmationCode='mg' where seatHoldId=167"))
				.thenReturn(query);

		Mockito.when(query.executeUpdate()).thenReturn(1);
		Mockito.when((session).getTransaction()).thenReturn(transaction);

		List<Integer> seatsList = new ArrayList<Integer>();
		seatsList.add(1100);
		Mockito.when(query.list()).thenReturn(seatsList);

		String confirmationReceived = ticketQuery.reserveSeats(seatHoldId,
				customerEmail);

		assertEquals("mg", confirmationReceived);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketQueryImpl#cancelReserveSeats(SeatHold)}
	 * .
	 * 
	 * @throws TicketManagementException
	 */
	@Test
	public void testCancelReserveSeats() throws TicketManagementException
	{

		PowerMockito.mockStatic(HibernateUtil.class);
		Mockito.when(HibernateUtil.getSessionFactory()).thenReturn(
				sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);

		Mockito.when(session.createQuery(Mockito.anyString()))
				.thenReturn(query);

		List<Integer> totalSeats = new ArrayList<Integer>();
		totalSeats.add(2);

		List<Object[]> rowSeatInfo = new ArrayList<Object[]>();
		Object[] obj = { 2, 2, 25, 100 };
		rowSeatInfo.add(obj);

		Mockito.when(query.list()).thenReturn(totalSeats, rowSeatInfo);

		Mockito.when((session).beginTransaction()).thenReturn(transaction);
		Mockito.when(query.executeUpdate()).thenReturn(1);
		Mockito.when((session).getTransaction()).thenReturn(transaction);

		ticketQuery.cancelReserveSeats(seatHold);
		assertTrue(true);
	}
}
