/**
 * 
 */
package TicketManagement.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Madhuri Gurumurthy
 * 
 *         This is a test class for the TicketValidationImpl
 *
 */
public class TicketValidationImplTest
{
	private TicketValidationImpl tcktValidImpl;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		tcktValidImpl = new TicketValidationImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		tcktValidImpl = null;
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#isLevelIdValid(java.util.Optional)}
	 * .
	 */
	@Test
	public void testIsLevelIdValidPositiveCase1()
	{
		tcktValidImpl.isLevelIdValid(1);
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#isLevelIdValid(java.util.Optional)}
	 * .
	 */
	@Test
	public void testIsLevelIdValidPositiveCase2()
	{
		tcktValidImpl.isLevelIdValid(4);
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#isLevelIdValid(java.util.Optional)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsLevelIdValidNegative()
	{
		tcktValidImpl.isLevelIdValid(0);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#isNumSeatsValid(int)}
	 * .
	 */
	@Test
	public void testIsNumSeatsValidPositive()
	{
		tcktValidImpl.isNumSeatsValid(12);
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#isNumSeatsValid(int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsNumSeatsValidNegative()
	{
		tcktValidImpl.isNumSeatsValid(0);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#validateEmail(String)}
	 * .
	 * 
	 * @throws TicketManagementException
	 */
	@Test
	public void testValidateEmail() throws TicketManagementException
	{
		tcktValidImpl.validateEmail("mg@gmail.com");
		assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link TicketManagement.service.TicketValidationImpl#validateEmail(String)}
	 * .
	 * 
	 * @throws TicketManagementException
	 */
	@Test
	public void testValidateEmailNegative()
	{
		try
		{
			tcktValidImpl.validateEmail("mggmail.com");
		} catch (TicketManagementException e)
		{
			assertTrue(true);
		}
	}

}
