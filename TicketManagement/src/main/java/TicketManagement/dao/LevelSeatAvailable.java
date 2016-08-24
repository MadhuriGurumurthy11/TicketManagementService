package TicketManagement.dao;

/**
 * 
 */

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 * 
 *         This class holds the information about the start row available and
 *         the start seat in the row.
 * 
 *         </p>
 *
 */
public class LevelSeatAvailable
{
	private int levelId;
	private int rowAvailable;
	private int seatAvailable;

	/**
	 * @return the levelId
	 */
	public int getLevelId()
	{
		return levelId;
	}

	/**
	 * @param levelId
	 *            the levelId to set
	 */
	public void setLevelId(int levelId)
	{
		this.levelId = levelId;
	}

	/**
	 * @return the rowAvailable
	 */
	public int getRowAvailable()
	{
		return rowAvailable;
	}

	/**
	 * @param rowAvailable
	 *            the rowAvailable to set
	 */
	public void setRowAvailable(int rowAvailable)
	{
		this.rowAvailable = rowAvailable;
	}

	/**
	 * @return the seatAvailable
	 */
	public int getSeatAvailable()
	{
		return seatAvailable;
	}

	/**
	 * @param seatAvailable
	 *            the seatAvailable to set
	 */
	public void setSeatAvailable(int seatAvailable)
	{
		this.seatAvailable = seatAvailable;
	}

}
