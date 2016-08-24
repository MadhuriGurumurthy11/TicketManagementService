/**
 * 
 */
package TicketManagement.dao;

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 * 
 *         This class holds information about the level, price and seating
 *         arrangement.
 * 
 *         </p>
 *
 */
public class LevelInfo
{
	private int levelId;
	private String levelName;
	private int price;
	private int rows;
	private int seats;
	private int totalSeats;
	private int totalAvailableSeats;

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
	 * @return the levelName
	 */
	public String getLevelName()
	{
		return levelName;
	}

	/**
	 * @param levelName
	 *            the levelName to set
	 */
	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}

	/**
	 * @return the price
	 */
	public int getPrice()
	{
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(int price)
	{
		this.price = price;
	}

	/**
	 * @return the rows
	 */
	public int getRows()
	{
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows)
	{
		this.rows = rows;
	}

	/**
	 * @return the seats
	 */
	public int getSeats()
	{
		return seats;
	}

	/**
	 * @param seats
	 *            the seats to set
	 */
	public void setSeats(int seats)
	{
		this.seats = seats;
	}

	/**
	 * @return the totalSeats
	 */
	public int getTotalSeats()
	{
		return totalSeats;
	}

	/**
	 * @param totalSeats
	 *            the totalSeats to set
	 */
	public void setTotalSeats(int totalSeats)
	{
		this.totalSeats = totalSeats;
	}

	/**
	 * @return the totalAvailableSeats
	 */
	public int getTotalAvailableSeats()
	{
		return totalAvailableSeats;
	}

	/**
	 * @param totalAvailableSeats
	 *            the totalAvailableSeats to set
	 */
	public void setTotalAvailableSeats(int totalAvailableSeats)
	{
		this.totalAvailableSeats = totalAvailableSeats;
	}

}
