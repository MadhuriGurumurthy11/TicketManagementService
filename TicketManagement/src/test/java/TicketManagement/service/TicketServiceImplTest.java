package TicketManagement.service;

/**
 * 
 */

import java.util.Optional;

import junit.framework.TestCase;

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
 *         This is a test class for TicketServiceImpl
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TicketQueryImpl.class, TicketServiceImpl.class })
public class TicketServiceImplTest extends TestCase
{
	private TicketServiceImpl tcktSrvImpl;
	private TicketQueryImpl ticketQuery;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		tcktSrvImpl = new TicketServiceImpl();
		ticketQuery = Mockito.mock(TicketQueryImpl.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		tcktSrvImpl = null;
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#numSeatsAvailable(java.util.Optional)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNumOfSeatsAvailableNegative()
	{
		Integer level = new Integer(5);
		Optional<Integer> venueLevel = Optional.of(level);
		tcktSrvImpl.numSeatsAvailable(venueLevel);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#numSeatsAvailable(java.util.Optional)}
	 * .
	 */
	@Test
	public void testNumOfSeatsAvailablePositive()
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Mockito.doReturn(6250).when(ticketQuery).getTotalAvailableSeats();

		int seats = tcktSrvImpl.numSeatsAvailable(Optional.empty());

		assertEquals(seats, 6250);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#getAvailSeatsOfALevel(int)}
	 * .
	 */
	@Test
	public void testNumOfSeatsAvailableForALevelPositive()
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Integer level = new Integer(1);
		Mockito.doReturn(1250).when(ticketQuery)
				.getAvailSeatsOfALevel(level.intValue());

		int seats = tcktSrvImpl.numSeatsAvailable(Optional.of(level));

		assertEquals(seats, 1250);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#reserveSeats(int,String)}
	 * .
	 */
	@Test
	public void testReserveSeats() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Mockito.doReturn("mg").when(ticketQuery)
				.reserveSeats(123, "mg@gmail.com");

		String confirmation = tcktSrvImpl.reserveSeats(123, "mg@gmail.com");

		assertEquals(confirmation, "mg");
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#cancelReserveSeats(SeatHold)}
	 * .
	 */
	@Test
	public void testCancelHeldSeats() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		SeatHold seatHold = new SeatHold();
		seatHold.setLevelId(3);
		seatHold.setEmailId("mg@gmail.com");
		seatHold.setNoOfSeats(3);
		seatHold.setRowNumber("1,2");
		seatHold.setSeatHoldId(13);
		seatHold.setSeatNumbers("24,25,1");
		seatHold.setStatus("HELD");

		Mockito.doNothing().when(ticketQuery).cancelReserveSeats(seatHold);

		tcktSrvImpl.cancelHeldSeats(seatHold);
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#findAndHoldSeats(int, Optional,Optional,String)}
	 * .
	 */
	@Test(expected = TicketManagementException.class)
	public void testFindAndHoldSeatsNegative() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int numSeats = 3;

		Integer min = new Integer(2);
		Optional<Integer> minLevel = Optional.of(min);

		Integer max = new Integer(3);
		Optional<Integer> maxLevel = Optional.of(max);

		String customerEmail = "mggmail.com";

		SeatHold seatHold = new SeatHold();
		seatHold.setLevelId(3);
		seatHold.setEmailId(customerEmail);
		seatHold.setNoOfSeats(numSeats);
		seatHold.setRowNumber("1,2");
		seatHold.setSeatHoldId(13);
		seatHold.setSeatNumbers("24,25,1");
		seatHold.setStatus("HELD");

		Mockito.doReturn(seatHold)
				.when(ticketQuery)
				.findAndHoldSeats(numSeats, min.intValue(), max.intValue(),
						customerEmail);

		SeatHold seatHoldRecceived = tcktSrvImpl.findAndHoldSeats(numSeats,
				minLevel, maxLevel, customerEmail);

		assertEquals(13, seatHoldRecceived.getSeatHoldId());
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#findAndHoldSeats(int, Optional,Optional,String)}
	 * .
	 */
	@Test
	public void testFindAndHoldSeatsCase1() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int numSeats = 3;

		Integer min = new Integer(2);
		Optional<Integer> minLevel = Optional.of(min);

		Integer max = new Integer(3);
		Optional<Integer> maxLevel = Optional.of(max);

		String customerEmail = "mg@gmail.com";

		SeatHold seatHold = new SeatHold();
		seatHold.setLevelId(3);
		seatHold.setEmailId(customerEmail);
		seatHold.setNoOfSeats(numSeats);
		seatHold.setRowNumber("1,2");
		seatHold.setSeatHoldId(13);
		seatHold.setSeatNumbers("24,25,1");
		seatHold.setStatus("HELD");

		Mockito.doReturn(seatHold)
				.when(ticketQuery)
				.findAndHoldSeats(numSeats, min.intValue(), max.intValue(),
						customerEmail);

		SeatHold seatHoldRecceived = tcktSrvImpl.findAndHoldSeats(numSeats,
				minLevel, maxLevel, customerEmail);

		assertEquals(13, seatHoldRecceived.getSeatHoldId());
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#findAndHoldSeats(int, Optional,Optional,String)}
	 * .
	 */
	@Test
	public void testFindAndHoldSeatsCase2() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int numSeats = 3;

		Integer min = new Integer(0);
		Optional<Integer> minLevel = Optional.of(min);

		Integer max = new Integer(3);
		Optional<Integer> maxLevel = Optional.of(max);

		String customerEmail = "mg@gmail.com";

		SeatHold seatHold = new SeatHold();
		seatHold.setLevelId(max.intValue());
		seatHold.setEmailId(customerEmail);
		seatHold.setNoOfSeats(numSeats);
		seatHold.setRowNumber("1,2");
		seatHold.setSeatHoldId(15);
		seatHold.setSeatNumbers("24,25,1");
		seatHold.setStatus("HELD");

		Mockito.doReturn(seatHold).when(ticketQuery)
				.findAndHoldSeats(numSeats, 1, max.intValue(), customerEmail);

		SeatHold seatHoldRecceived = tcktSrvImpl.findAndHoldSeats(numSeats,
				minLevel, maxLevel, customerEmail);

		assertEquals(15, seatHoldRecceived.getSeatHoldId());
		assertEquals(max.intValue(), seatHoldRecceived.getLevelId());
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#findAndHoldSeats(int, Optional,Optional,String)}
	 * .
	 */
	@Test
	public void testFindAndHoldSeatsCase3() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int numSeats = 3;

		Integer min = new Integer(1);
		Optional<Integer> minLevel = Optional.of(min);

		Integer max = new Integer(0);
		Optional<Integer> maxLevel = Optional.of(max);

		String customerEmail = "mg@gmail.com";

		SeatHold seatHold = new SeatHold();
		seatHold.setLevelId(min.intValue());
		seatHold.setEmailId(customerEmail);
		seatHold.setNoOfSeats(numSeats);
		seatHold.setRowNumber("1,2");
		seatHold.setSeatHoldId(18);
		seatHold.setSeatNumbers("24,25,1");
		seatHold.setStatus("HELD");

		Mockito.doReturn(seatHold).when(ticketQuery)
				.findAndHoldSeats(numSeats, min.intValue(), 4, customerEmail);

		SeatHold seatHoldRecceived = tcktSrvImpl.findAndHoldSeats(numSeats,
				minLevel, maxLevel, customerEmail);

		assertEquals(18, seatHoldRecceived.getSeatHoldId());
		assertEquals(min.intValue(), seatHoldRecceived.getLevelId());
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketServiceImpl#findAndHoldSeats(int, Optional,Optional,String)}
	 * .
	 */
	@Test
	public void testFindAndHoldSeatsCase4() throws TicketManagementException
	{
		try
		{
			PowerMockito.whenNew(TicketQueryImpl.class).withNoArguments()
					.thenReturn(ticketQuery);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int numSeats = 3;

		Integer min = new Integer(0);
		Optional<Integer> minLevel = Optional.of(min);

		Integer max = new Integer(0);
		Optional<Integer> maxLevel = Optional.of(max);

		String customerEmail = "mg@gmail.com";

		SeatHold seatHold = new SeatHold();
		seatHold.setLevelId(2);
		seatHold.setEmailId(customerEmail);
		seatHold.setNoOfSeats(numSeats);
		seatHold.setRowNumber("1,2");
		seatHold.setSeatHoldId(18);
		seatHold.setSeatNumbers("24,25,1");
		seatHold.setStatus("HELD");

		Mockito.doReturn(seatHold).when(ticketQuery)
				.findAndHoldSeats(numSeats, 1, 4, customerEmail);

		SeatHold seatHoldRecceived = tcktSrvImpl.findAndHoldSeats(numSeats,
				minLevel, maxLevel, customerEmail);

		assertEquals(18, seatHoldRecceived.getSeatHoldId());
		assertEquals(2, seatHoldRecceived.getLevelId());
	}
}
