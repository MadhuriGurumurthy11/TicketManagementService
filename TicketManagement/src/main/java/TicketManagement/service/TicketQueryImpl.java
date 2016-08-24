package TicketManagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import TicketManagement.dao.SeatHold;

/**
 * 
 */

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 *         This is the query class that talks to database by creating the
 *         queries.
 * 
 *         The methods are:
 * 
 *         1. The method to fetch the total available seats of the venue.
 * 
 *         2. The method to fetch the total available seats of a given level.
 * 
 *         3. The method finds and holds the seats according to user
 *         requirement.
 * 
 *         4. The method reserves the seats held.
 *         </p>
 *
 */
public class TicketQueryImpl
{

	/**
	 * This method fetches the Total available seats of the venue
	 * 
	 * @return int total number of seats
	 */
	public int getTotalAvailableSeats()
	{

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session
				.createQuery("select totalAvailableSeats from LevelInfo");
		List result = query.list();

		session.close();
		int sumSeats = 0;
		for (int i = 0; i < result.size(); i++)
		{
			sumSeats += (int) result.get(i);
		}
		return sumSeats;

	}

	/**
	 * This method fetches the Total available seats of a given level.
	 * 
	 * 
	 * @param levelId
	 * @return int total number of available seats of a particular level
	 */
	public int getAvailSeatsOfALevel(int levelId)
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session
				.createQuery("select totalAvailableSeats from LevelInfo where levelId="
						+ levelId);
		List result = query.list();

		session.close();
		return (int) result.get(0);
	}

	/**
	 * This method finds and holds the seats according to user requirement.
	 * 
	 * @param numSeats
	 * @param intValue
	 * @param intValue2
	 * @param customerEmail
	 * @return SeatHold
	 * @throws TicketManagementException
	 */
	public SeatHold findAndHoldSeats(int numSeats, int minimumLevel,
			int maximumLevel, String customerEmail)
			throws TicketManagementException
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		SeatHold seatHold = new SeatHold();
		try
		{

			Query query = session
					.createQuery("select levelId,totalAvailableSeats,rows,seats from LevelInfo");
			List<Object[]> results = query.list();

			Map<Integer, Integer> resultsMap = new HashMap<Integer, Integer>();
			for (int m = 0; m < results.size(); m++)
			{
				Object[] x = results.get(m);
				resultsMap.put(((Integer) x[0]), ((Integer) x[1]));
			}
			// -------------------------------------------------------------------------
			int totalAvailableSeats = 0;
			int rows = 0;
			int seats = 0;
			int rowToBeSet = 0;
			int seatToBeSet = 0;

			if (minimumLevel == 0 && maximumLevel == 0)
			{
				while (minimumLevel < results.size()
						&& (!(resultsMap.get(new Integer((minimumLevel + 1)))
								.intValue() >= numSeats)))
				{
					minimumLevel++;
				}
			} else
			{
				if (minimumLevel == 0 && maximumLevel != 0)
				{
					minimumLevel = 0;
				}

				else if (minimumLevel != 0 && maximumLevel == 0)
				{
					minimumLevel = 3;

				} else
				{
					minimumLevel--;
					maximumLevel--;
				}

				while (minimumLevel <= maximumLevel
						&& (!(resultsMap.get(new Integer(minimumLevel + 1))
								.intValue() >= numSeats)))
				{
					minimumLevel++;
				}

			}
			if (minimumLevel < results.size())
			{
				totalAvailableSeats = resultsMap.get(
						new Integer(minimumLevel + 1)).intValue();
				Object[] s = results.get(minimumLevel);
				rows = ((Integer) s[2]).intValue();
				seats = ((Integer) s[3]).intValue();
				seatHold.setEmailId(customerEmail);
				seatHold.setLevelId(minimumLevel + 1);
				seatHold.setNoOfSeats(numSeats);

				query = session
						.createQuery("select rowAvailable,seatAvailable from LevelSeatAvailable where levelId="
								+ (minimumLevel + 1));

				List<Object[]> seatDetails = query.list();
				s = seatDetails.get(0);
				StringBuilder rowNumbers = new StringBuilder();
				rowNumbers.append(((Integer) s[0]).intValue() + "");
				StringBuilder seatNumbers = new StringBuilder();
				s = seatDetails.get(0);
				int initialSeat = (((Integer) s[1]).intValue());
				int k = initialSeat;
				rowToBeSet = ((Integer) s[0]).intValue();

				int rowNo = ((Integer) s[0]).intValue();
				for (int j = 0; j < numSeats; j++)
				{

					if (k <= seats)
					{
						seatNumbers.append((k) + ",");
						k++;
					} else
					{
						rowNumbers.append("," + ++rowNo);
						k = 1;
						rowToBeSet++;
					}

				}
				seatToBeSet = k;

				seatHold.setRowNumber(rowNumbers.toString());
				String seatNos = "";
				if (seatNumbers.length() > 1)
				{
					seatNos = seatNumbers.substring(0,
							(seatNumbers.length() - 1));
				} else
				{
					seatNos = seatNumbers.toString();
				}
				seatHold.setSeatNumbers(seatNos);
				seatHold.setStatus("HELD");
				seatHold.setConfirmationCode(null);

				query = session
						.createQuery("select max(seatHoldId) from SeatHold");
				List id = query.list();
				int seatHoldId = 0;
				if (id.get(0) == null)
				{
					seatHoldId = 1;
				} else
				{
					seatHoldId = ((Integer) id.get(0)).intValue() + 1;
				}
				seatHold.setSeatHoldId(seatHoldId);

				session.beginTransaction();

				session.save(seatHold);
				session.getTransaction().commit();

				session.beginTransaction();
				query = session
						.createQuery("update LevelInfo set totalAvailableSeats="
								+ (totalAvailableSeats - numSeats)
								+ " where levelId=" + (minimumLevel + 1));
				query.executeUpdate();

				query = session
						.createQuery("update LevelSeatAvailable set seatAvailable="
								+ seatToBeSet
								+ " , rowAvailable="
								+ rowToBeSet
								+ " where levelId=" + (minimumLevel + 1));
				query.executeUpdate();
				session.getTransaction().commit();

			}
		} catch (Exception e)
		{
			throw new TicketManagementException(
					"Oops! Something went wrong. Could not find the seats.");
		} finally
		{
			if (session.isOpen())
				session.close();
		}
		return seatHold;
	}

	/**
	 * This method reserves the seats held.
	 * 
	 * @param seatHoldId
	 * @param customerEmail
	 * @return String ConfirmationCode
	 * @throws TicketManagementException
	 */
	public String reserveSeats(int seatHoldId, String customerEmail)
			throws TicketManagementException
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String confirmation = "";
		try
		{
			String status = "Reserved";
			String[] confirm = customerEmail.split("@");
			session.beginTransaction();
			Query query = session.createQuery("update SeatHold set status='"
					+ status + "' ,confirmationCode='" + confirm[0]
					+ "' where seatHoldId=" + seatHoldId);
			query.executeUpdate();
			session.getTransaction().commit();
			confirmation = confirm[0];
		} catch (Exception e)
		{
			throw new TicketManagementException(
					"Oops! Something went wrong. Could not reserve the seats.");
		} finally
		{
			if (session.isOpen())
				session.close();
		}
		return confirmation;
	}

	/**
	 * This method is used to cancel the seat held for the user.
	 * 
	 * @param seatHold
	 *            The seat previously held
	 * @throws TicketManagementException
	 */
	public void cancelReserveSeats(SeatHold seatHold)
			throws TicketManagementException
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query query;
		try
		{
			// Set the total seat nos, start row and seat no, cancel the
			// reservations entry

			session.beginTransaction();

			query = session
					.createQuery("select totalAvailableSeats from LevelInfo where levelId="
							+ seatHold.getLevelId());
			List sumSeats = query.list();
			int totalSeats = ((Integer) sumSeats.get(0)).intValue()
					+ seatHold.getNoOfSeats();
			query = session
					.createQuery("update LevelInfo set totalAvailableSeats="
							+ totalSeats + "where levelId="
							+ seatHold.getLevelId());
			query.executeUpdate();

			query = session
					.createQuery("select l.rowAvailable,l.seatAvailable,i.rows,i.seats from LevelSeatAvailable l,LevelInfo i"
							+ " where l.levelId=i.levelId and i.levelId="
							+ seatHold.getLevelId());

			List<Object[]> rowSeatInfo = query.list();
			Object[] obj = rowSeatInfo.get(0);
			int rowAvailable = ((Integer) obj[0]).intValue();
			int seatAvailable = ((Integer) obj[1]).intValue();
			int rows = ((Integer) obj[2]).intValue();
			int seats = ((Integer) obj[3]).intValue();
			;

			seatAvailable -= seatHold.getNoOfSeats();
			if (seatAvailable < 1)
			{
				seatAvailable = seats - seatAvailable;
				rowAvailable--;
			}

			query = session
					.createQuery("update LevelSeatAvailable set rowAvailable="
							+ rowAvailable + ",seatAvailable=" + seatAvailable
							+ " where levelId=" + seatHold.getLevelId());
			query.executeUpdate();

			query = session
					.createQuery("delete from SeatHold where seatHoldId="
							+ seatHold.getSeatHoldId());
			query.executeUpdate();

			session.getTransaction().commit();
		} catch (Exception e)
		{
			throw new TicketManagementException(
					"Oops! Something went wrong. Could not cancel the held seats.");

		} finally
		{
			if (session.isOpen())
				session.close();
		}
	}
}
